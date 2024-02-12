package main

import (
	"fmt"
	"lgtmoon-api/internal/database"
	"lgtmoon-api/internal/model"
	"os"

	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
)

func main() {
	// os.Args[0] にコマンド本体、 os.Args[1] に引数が入るので、長さ2以上必要
	if (len(os.Args) < 2) {
		fmt.Println("Command Required e.g. lgtmoon-api serve")
		fmt.Println("command: serve, migrate, ...")
	}
	command := os.Args[1]
	fmt.Println("Command: " + command)

	// godotenv.Load のタイミングで err が返ってくる場合があるが、
	// .env に定義されていなくても環境変数に定義されていれば問題ないので無視して続ける
	godotenv.Load()

	switch command {
	case "db:migrate":
		db, err := database.Connect()
		if err != nil {
			fmt.Println("Database Connection Error")
			fmt.Println(err)
			return
		}
		db.AutoMigrate(&model.Image{})
		fmt.Println("Complete")

	// ライブリロードを行う gin に対応するために default をサーバー起動にしている
	default:
		r := gin.Default()
		port := os.Getenv("PORT")
		// 環境変数が設定されていない場合は空文字になる
		if port == "" {
			port = "8080"
		}
		r.GET("/health", func(c *gin.Context) {
			c.JSON(200, gin.H{
				"message": "OK",
			})
		})
		r.Run("0.0.0.0:" + port)
	}
}
