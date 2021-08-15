package storage

import java.io.{File, FileInputStream}

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.auth.{AWSCredentials, AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.{AccessControlList, CannedAccessControlList, ObjectMetadata, PutObjectResult}
import com.google.inject.Inject
import image.ConvertedImage
import org.apache.tika.Tika
import play.api.{Configuration, Logging}

/**
 * https://github.com/seratch/AWScala
 */
class ImageWasabiS3 @Inject() (
  config: Configuration
) extends image.ImageStorage
  with Logging {

  private val bucketName = config.get[String]("wasabiS3.lgtmImage.bucket")
  // 画像を S3 から配信するときのキャッシュ設定
  // cdn がどれくらいの時間キャッシュするかに関係する
  // 31536000 = 365日（365 * 24 * 60 * 60）
  // https://developer.mozilla.org/ja/docs/Web/HTTP/Headers/Cache-Control
  private val cacheControl = "public, max-age=31536000, immutable"

  private def credentials(): AWSCredentials = {
    val accessKeyIdOpt = config.getOptional[String]("wasabiS3.accessKeyId")
    val secretAccessKeyOpt = config.getOptional[String]("wasabiS3.secretAccessKey")
    (accessKeyIdOpt, secretAccessKeyOpt) match {
      case (Some(accessKeyId), Some(secretAccessKey)) =>
        new BasicAWSCredentials(accessKeyId, secretAccessKey)
      case _ =>
        // env や config に設定されていない場合は ~/.aws/credentials の設定から取る
        // （ローカル開発用）
        logger.info("環境変数から AWSCredentials が取得できなかったので ~/.aws/credentials から取得します")
        val providerName = config.get[String]("wasabiS3.provider")
        new ProfileCredentialsProvider(providerName).getCredentials
    }
  }

  /**
   * S3 リクエスト時に Content-Type を付与しないと、
   * ホスティングする時に正常に Content-Type が付与されず、
   * ブラウザで正しく表示されないので、
   * アップロードするファイルから Content-Type を推測する
   * @param file このファイルの Content-Type を推測する
   * @return
   */
  private def detectContentType(file: File): Option[String] = {
    try {
      val tika = new Tika()
      val contentType = tika.detect(file)
      Some(contentType)
    } catch {
      case e: Exception =>
        logger.error("ファイルのContentTypeを推定しようとしてエラー", e)
        None
    }
  }

  /**
   * S3 に PUT する際に必要な ObjectMetadata を生成する
   * @param contentLength Content-Length
   * @param contentType Content-Type
   * @return
   */
  private def objectMetadata(contentLength: Long, contentType: String): ObjectMetadata = {
    val metadata = new ObjectMetadata()
    metadata.setContentLength(contentLength)
    metadata.setContentType(contentType)
    metadata.setCacheControl(cacheControl)
    metadata
  }

  /**
   * S3 にオブジェクトを PUT する
   * @param bucketName バケット名
   * @param key オブジェクトのキー
   * @param fileInputStream PUTするファイル
   * @param metadata Content-Type などのメタデータ
   * @return
   */
  private def putObject(
    bucketName: String,
    key: String,
    fileInputStream: FileInputStream,
    metadata: ObjectMetadata
  ): Option[PutObjectResult] = {
    val s3 = AmazonS3ClientBuilder.standard()
      // https でリクエストするとなぜか証明書エラーになるので http でリクエストするように変更した
      // いつか直したほうが良いかも
      // ここが証明書エラー https://image.lgtmoon.dev.s3.ap-northeast-1-ntt.wasabisys.com/
      .withEndpointConfiguration(new EndpointConfiguration("http://s3.ap-northeast-1-ntt.wasabisys.com/", "ap-northeast-1"))
      .withCredentials(new AWSStaticCredentialsProvider(credentials()))
      .build()
    try {
      logger.info(s"Wasabi S3 にファイルを PUT します\tbucket:${bucketName}\tkey:${key}")
      val result = s3.putObject(
        bucketName,
        key,
        fileInputStream,
        metadata
      )
      // アップロードした後に Public アクセスを可能にする
      logger.info(s"Wasabi S3 に PUT されたファイルを Publish します\tbucket:${bucketName}\tkey:${key}")
      s3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead)
      Option(result)
    } catch {
      case e: Exception =>
        logger.error("LGTM画像を Wasabi S3 にアップロードしようとしてエラー", e)
        None
    }
  }

  /**
   * 変換した画像を渡すと S3 に保存する
   * @param convertedImage LGTM画像
   */
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
