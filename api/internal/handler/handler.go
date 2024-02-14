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
	fmt.Println("CreateImage")
	imageRepository := repository.ImageRepository{}
	image := model.Image{
		ContentType: "image/png",
		Status: model.IMAGE_NOT_AVAILABLE,
	}
	imageRepository.Create(image)
}
