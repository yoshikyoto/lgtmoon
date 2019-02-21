const cpx = require('cpx')

const src = './dist'
const dest = '../public'
const template = '../app/views';

cpx.copySync(`${src}/*.html`, `${template}/`)
cpx.copySync(`${src}/assets/js/*.{js,js.map}`, `${dest}/js/`)
cpx.copySync(`${src}/assets/css/*.css`, `${dest}/css/`)
