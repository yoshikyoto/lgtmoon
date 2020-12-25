package storage

import java.io.File

import awscala.s3.{Bucket, S3}
import awscala.Region
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.google.inject.Inject
import image.ConvertedImage
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

  private def credentials(): (String, String) = {
    val accessKeyIdOpt = config.getOptional[String]("aws.accessKeyId")
    val secretAccessKeyOpt = config.getOptional[String]("aws.secretAccessKey")
    (accessKeyIdOpt, secretAccessKeyOpt) match {
      case (Some(accessKeyId), Some(secretAccessKey)) => (accessKeyId, secretAccessKey)
      case _ => {
        // env や config に設定されていない場合は ~/.aws/credentials の設定から取る
        // （ローカル開発用）
        logger.info("環境変数からAWSのCredentialsが取得できなかったので ~/.aws/credentials から取得します")
        val providerName = config.get[String]("aws.provider")
        val credentials = new ProfileCredentialsProvider(providerName).getCredentials
        (credentials.getAWSAccessKeyId, credentials.getAWSSecretKey)
      }
    }
  }

  def save(convertedImage: ConvertedImage): Unit = {
    val (accessKeyId, secretKey) = credentials()
    val bucket = new Bucket(bucketName)
    val s3: S3 = S3(accessKeyId, secretKey)(Region.AP_NORTHEAST_1)
    val file = new File(convertedImage.path)
    s3.put(bucket, convertedImage.id.toString, file)
  }
}
