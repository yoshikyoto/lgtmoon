package api

import (
	"net/http"
	"net/http/httptest"
	"testing"
)

func TestPostImage(t *testing.T) {
	SetUp(t)
	r := GetRouter()
	w := httptest.NewRecorder()
	request, _ := http.NewRequest("POST", "/api/images", nil)
	r.ServeHTTP(w, request)

	if (w.Code != 200) {
		t.Fatal("Expected Response status code 200, Actual: ", w.Code)
	}
}
