package storage

import java.io.File

import awscala.s3.{Bucket, S3}
import awscala.Region
import com.google.inject.Inject
import play.api.Configuration

/**
 * https://github.com/seratch/AWScala
 */
class ImageStorage @Inject() (
  config: Configuration
) extends image.ImageStorage {
  val awsAccessKeyId: String = config.get[String]("aws.accessKeyId")
  val awsSecretAccessKey: String = config.get[String]("aws.secretAccessKey")
  val awsS3BucketName: String = config.get[String]("aws.s3.imageBucket")

  implicit val s3: S3 = S3.at(Region.AP_NORTHEAST_1)
  val bucket: Bucket = s3.bucket(awsS3BucketName).get

  def save(id: Int, localAbsolutePath: String): Unit = {
    val file = new File(localAbsolutePath)
  }
}
