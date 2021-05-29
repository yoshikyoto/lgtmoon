package storage

import java.io.{File, FileInputStream}

import com.amazonaws.auth.{AWSCredentials, AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.{ObjectMetadata, PutObjectResult}
import com.google.inject.Inject
import image.ConvertedImage
import org.apache.tika.Tika
import play.api.Configuration
import play.api.Logging

/**
 * https://github.com/seratch/AWScala
 */
class ImageS3 @Inject() (
  config: Configuration
) extends image.ImageStorage
  with Logging {

  private val bucketName = config.get[String]("aws.s3.imageBucket")
  // 画像を S3 から配信するときのキャッシュ設定
  // cdn がどれくらいの時間キャッシュするかに関係する
  // 31536000 = 365日（365 * 24 * 60 * 60）
  // https://developer.mozilla.org/ja/docs/Web/HTTP/Headers/Cache-Control
  private val cacheControl = "public, max-age=31536000, immutable"

  private def credentials(): AWSCredentials = {
    val accessKeyIdOpt = config.getOptional[String]("aws.accessKeyId")
    val secretAccessKeyOpt = config.getOptional[String]("aws.secretAccessKey")
    (accessKeyIdOpt, secretAccessKeyOpt) match {
      case (Some(accessKeyId), Some(secretAccessKey)) =>
        new BasicAWSCredentials(accessKeyId, secretAccessKey)
      case _ =>
        // env や config に設定されていない場合は ~/.aws/credentials の設定から取る
        // （ローカル開発用）
        logger.info("環境変数からAWSのCredentialsが取得できなかったので ~/.aws/credentials から取得します")
        val providerName = config.get[String]("aws.provider")
        new ProfileCredentialsProvider(providerName).getCredentials
    }
  }

  private def detectContentType(file: File): Option[String] = {
    try {
      val tika = new Tika()
      val contentType = tika.detect(file)
      return Some(contentType)
    } catch {
      case e: Exception =>
        logger.error("ファイルのContentTypeを推定しようとしてエラー", e)
        return None
    }
  }

  private def objectMetadata(contentLength: Long, contentType: String): ObjectMetadata = {
    val metadata = new ObjectMetadata()
    metadata.setContentLength(contentLength)
    metadata.setContentType(contentType)
    metadata.setCacheControl(cacheControl)
    metadata
  }

  private def putObject(
    bucketName: String,
    key: String,
    fileInputStream: FileInputStream,
    metadata: ObjectMetadata
  ): Option[PutObjectResult] = {
    val s3 = AmazonS3ClientBuilder.standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials()))
      .withRegion(Regions.AP_NORTHEAST_1)
      .build()
    try {
      val result = s3.putObject(
        bucketName,
        key,
        fileInputStream,
        metadata
      )
      Option(result)
    } catch {
      case e: Exception =>
        logger.error("LGTM画像をS3にPUTできなかった", e)
        None
    }
  }

  def save(convertedImage: ConvertedImage): Boolean = {
    val file = new File(convertedImage.path)
    val fileInputStream = new FileInputStream(file)
    detectContentType(file) match {
      case None => false
      case Some(contentType) =>
        putObject(
          bucketName,
          convertedImage.id.toString,
          fileInputStream,
          objectMetadata(file.length(), contentType)
        ) match {
          case Some(_) => true
          case None => false
        }
    }
  }
}
