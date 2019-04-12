const cpx = require('cpx')

const src = './dist'
const dest = '../public'
const template = '../app/views'

// html
const htmls = ['index']
for (const html of htmls) {
  cpx.copySync(`${src}/${html}.html`, `${template}/${html}.scala.html`)

  console.log(html)
}

// css, script
cpx.copySync(`${src}/assets/js/*.{js,js.map}`, `${dest}/js/`)
cpx.copySync(`${src}/assets/css/*.css`, `${dest}/css/`)
