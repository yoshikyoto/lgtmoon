package database

import (
	"fmt"
	"os"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

func Connect() (*gorm.DB, error) {
	// https://gorm.io/ja_JP/docs/connecting_to_the_database.html
	user := os.Getenv("DATABASE_USER")
	password := os.Getenv("DATABASE_PASSWORD")
	host := os.Getenv("DATABASE_HOST")
	port := os.Getenv("DATABASE_PORT")
	dbName := "lgtmoon"

	// parseTime=true を指定すると時刻が time.Time にパースされる
	parameter := "charset=utf8mb4&parseTime=True"

	dsn := fmt.Sprintf(
		"%s:%s@tcp(%s:%s)/%s?%s",
		user, password, host, port, dbName, parameter,
	)
	config := gorm.Config{}
	return gorm.Open(mysql.Open(dsn), &config)
}