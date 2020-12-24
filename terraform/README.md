## はじめに

### 初回のみ

tfenv の利用を推奨します。

Mac の場合は brew でインストールできます。

```sh
brew install tfenv
```

tfenv を使って terraform をインストールします。 
`.terraform-version` に書いてあるバージョンと、同じバージョンの terraform がインストールされます。

```sh
rfenv install
terraform --version                                                                                          [master]
```

プラグイン等のインストールと設定をします。

```sh
terraform init
```

### AWS 認証情報

`~/.aws/credentials` に以下の設定を追加してください。

```sh
[lgtmoon]
aws_access_key_id = XXXXX
aws_secret_access_key = XXXXXXXXXX
```

### terraform 実行

workspace と ver ファイルを正しく設定して下さい。
環境が混ざると、 tfstate ファイル（現在のリソース状況を表すファイル）が壊れます。

#### 開発環境に対して実行する

```sh
# workspace を dev にスイッチする
terraform workspace select dev

# 実行計画をたてる
# 表示された実行計画に問題がないか確認する
terraform plan -var-file=dev.tfvars -out=dev.plan

# 実行する
terraform apply dev.plan
```

実行後、 state ファイルをコミットして下さい。

#### 本番環境に対して実行する

```sh
terraform workspace select prod
terraform plan -var-file=prod.tfvars -out=prod.plan

terraform apply prod.plan
```

実行後、 state ファイルをコミットして下さい。

### Teraform で作成したリソースをすべて削除する

```sh
terraform destroy  -var-file=xxxx
```
