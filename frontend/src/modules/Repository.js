import axios from 'axios'

class Repository {
  constructor() {
  }

  recent() {
    return axios.get('/api/images/recent')
  }

  random() {
    return axios.get('/api/images/random')
  }

  search(keyword) {
    return axios.get('/api/search', {
      params: {
        keyword: keyword
      }
    })
  }

  generateByUrl(url) {
    return axios.post('/api/images/url', {
      url: url
    })
  }

  generateByFile(file) {
    console.log("generateByFile")
    var formData = new FormData()
    console.log(formData)
    formData.append('file', file)
    console.log(formData)
    return axios.post('/api/images/binary', formData)
  }

  unfavorite(item) {
    var favs = this.favorited()
    favs = favs.filter(fav => fav.url !== item.url)
    localStorage.setItem('favorites', JSON.stringify(favs))
  }

  favorite(item) {
    var favs = this.favorited()
    favs.unshift(item)
    localStorage.setItem('favorites', JSON.stringify(favs))
  }

  favorited() {
    var favs = null
    try {
      favs = JSON.parse(localStorage.getItem('favorites'))
    } catch (e) {
      return []
    }
    if (favs === null) {
      return []
    }
    return favs
  }

  isFavorited(item) {
    var favs = this.favorited()
    for (let fav of favs) {
      if (fav.url == item.url) {
        return true;
      }
    }
    return false
  }
}

export default new Repository();
