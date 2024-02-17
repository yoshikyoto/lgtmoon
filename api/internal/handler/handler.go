package handler

import (
	"fmt"
	"lgtmoon-api/internal/model"
	"lgtmoon-api/internal/repository"

	"github.com/gin-gonic/gin"
)

type Handler struct {
}

func (h Handler) LatestImages(c *gin.Context) {
}

func (h Handler) CreateImage(c *gin.Context) {
	imageRepository := repository.ImageRepository{}
	image := model.Image{
		ContentType: "image/png",
		Status: model.IMAGE_NOT_AVAILABLE,
	}
	createdImage, err := imageRepository.Create(image)
	if err != nil {
		c.JSON(500, ErrorResponse{Message: "サーバーでエラーが発生しました。しばらくしてから再度お試しください"})
		return
	}
	fmt.Println(createdImage)
}
