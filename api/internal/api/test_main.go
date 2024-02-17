package api

import (
	"testing"

	"github.com/joho/godotenv"
)

// api のテスト前に実行する処理
func SetUp(t *testing.T) {
	err := godotenv.Load("../../.env.testing")
	if err != nil {
		t.Error("Failed to load .env.testing.")
		t.Log(err)
	}
}
