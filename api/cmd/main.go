package main

import (
	"os"

	"github.com/gin-gonic/gin"
)

func main() {
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
