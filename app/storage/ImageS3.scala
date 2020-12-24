package storage

import java.io.File

import awscala.s3.{Bucket, S3}
import awscala.Region
import com.google.inject.Inject
import image.ConvertedImage
import play.api.Configuration

/**
 * https://github.com/seratch/AWScala
 */
class ImageS3 @Inject() (
  config: Configuration
) extends image.ImageStorage {
  // implicit val s3: S3 = S3.at(Region.AP_NORTHEAST_1)
  val awsAccessKeyId: String = config.get[String]("aws.accessKeyId")
  val awsSecretAccessKey: String = config.get[String]("aws.secretAccessKey")
  implicit val s3: S3 = S3(awsAccessKeyId, awsSecretAccessKey)(Region.AP_NORTHEAST_1)

  def save(convertedImage: ConvertedImage): Unit = {

    val awsS3BucketName: String = config.get[String]("aws.s3.imageBucket")
    val bucket: Bucket = s3.bucket(awsS3BucketName).get

    val file = new File(convertedImage.path)
    bucket.put(convertedImage.id.toString, file)
  }
}
