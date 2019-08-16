const cpx = require('cpx')

const src = './dist'
const dest = '../public'

console.log("Copy JS")
cpx.copySync(`${src}/assets/js/*.{js,js.map}`, `${dest}/js/`)

console.log("Copy CSS")
cpx.copySync(`${src}/assets/css/*.css`, `${dest}/css/`)

console.log("Copy HTML")
cpx.copySync(`${src}/*.html`, `${dest}/`)

console.log("Copy assets")
cpx.copySync(`${src}/assets/fonts/*`, `${dest}/fonts/`)
cpx.copySync(`${src}/assets/*.gif`, `${dest}/`)
