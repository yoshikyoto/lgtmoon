variable "lgtmoon_aws_iam_user_name" {
  description = "LGTMoonからAWSを利用するためのIAMユーザー名"
}
variable "lgtmoon_aws_s3_bucket_name" {
  description = "LGTM画像をアップロードするS3バケットの名前"
}
variable "lgtmoon_aws_s3_policy_name" {
  description = "LGTM画像をアップロードするS3バケットにアクセス可能なポリシー"
}

provider "aws" {
  # ~/.aws/credentials の [lgtmoon] を見る
  profile = "lgtmoon"

  # region は profile から読み込んでくれないので直接指定
  region = "ap-northeast-1"
}

# LGTMoon アプリケーションから利用するユーザー
resource "aws_iam_user" "lgtmoon_user" {
  name = var.lgtmoon_aws_iam_user_name
}

# S3バケットにパブリックからアクセスできるようなポリシー
data "aws_iam_policy_document" "lgtmoon_bucket_policy" {
  statement {
    effect = "Allow"
    principals {
      # 誰でもアクセスできる
      identifiers = ["*"]
      type = "*"
    }
    actions = ["s3:GetObject"]
    resources = [
      "arn:aws:s3:::${var.lgtmoon_aws_s3_bucket_name}",
      "arn:aws:s3:::${var.lgtmoon_aws_s3_bucket_name}/*"
    ]
  }
}

# S3 の public access block を無効に
resource "aws_s3_bucket_public_access_block" "lgtmoon_bucket_access_block" {
  bucket = var.lgtmoon_aws_s3_bucket_name
  block_public_acls = true
  block_public_policy = false
  ignore_public_acls = true
  restrict_public_buckets = false
}

# S3バケットを作成
resource "aws_s3_bucket" "lgtmoon_bucket" {
  bucket = var.lgtmoon_aws_s3_bucket_name
  policy = data.aws_iam_policy_document.lgtmoon_bucket_policy.json
  acl = "public-read"
}

# S3バケットにアクセスできるようなポリシー
resource "aws_iam_policy" "lgtmoon_s3_policy" {
  name = var.lgtmoon_aws_s3_policy_name
  description = "for upload to LGTMoon S3 bucket"
  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "s3:PutObject",
      "Resource": [
        "arn:aws:s3:::${var.lgtmoon_aws_s3_bucket_name}",
        "arn:aws:s3:::${var.lgtmoon_aws_s3_bucket_name}/*"
      ]
    }
  ]
}
EOF
}

# ポリシーをLGTMoonアプリケーションユーザーにアタッチ
resource "aws_iam_user_policy_attachment" "lgtmoon_s3_policy_attach" {
  user = aws_iam_user.lgtmoon_user.name
  policy_arn = aws_iam_policy.lgtmoon_s3_policy.arn
}
