package model

import "time"

type Image struct {
	id int
	contentType string
	createdAt time.Time
	status int
}
