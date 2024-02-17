package api

import (
	"lgtmoon-api/internal/handler"

	"github.com/gin-gonic/gin"
)

func GetRouter() *gin.Engine {
	r := gin.Default()

	handler := handler.Handler{}
	r.GET("/api/health", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "OK",
		})
	})
	r.POST("/api/images", handler.CreateImage)
	return r
}