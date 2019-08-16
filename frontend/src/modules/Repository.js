import axios from 'axios'

class Repository {
  constructor() {
  }

  recent() {
    return axios.get('/api/v1/images/recent')
  }

  random() {
    return axios.get('/api/v1/images/random')
  }

  search(keyword) {
    return axios.get('/api/v1/search', {
      params: {
        keyword: keyword
      }
    })
  }

  generateByUri(url) {
    return axios.post('/api/v1/images/url', {
      url: url
    })
  }

  favorite(item) {
    let favorite = localStorage.getItem('favorite')
    console.log(favorite)
  }
}

export default new Repository();
