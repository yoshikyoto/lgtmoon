package repository

import (
	"lgtmoon-api/internal/database"
	"lgtmoon-api/internal/model"
)

type ImageRepository struct {
}

func (r ImageRepository) Create(image model.Image) (*model.Image, error) {
	db, err := database.Connect()
	if err != nil {
		return nil, err
	}
	// create すると、 &image に ID と CreatedAt が入る
	result := db.Create(&image)

	if result.Error != nil {
		return nil, result.Error
	}
	return &image, nil
}
