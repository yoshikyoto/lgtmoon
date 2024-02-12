package model

import "time"

// gorm の書き方: https://gorm.io/ja_JP/docs/models.html
type Image struct {
	// ID という名前のフィールドは gorm が自動的に主キーとしてくれる
	ID uint
	ContentType string
	// CreatedAt というフィールド名は gorm が自動的に更新してくれる
	CreatedAt time.Time `gorm:"index"`
	Status Status
}

type Status int
const (
	// 変換前、変換失敗も全てこのステータスになる
	NOT_AVAILABLE Status = 0
	//
	AVAILABLE Status = 1
)
