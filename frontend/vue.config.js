module.exports = {
  filenameHashing: false,
  outputDir: './dist',
  assetsDir: 'assets',
  lintOnSave: false,
  devServer: {
    proxy: 'http://localhost:9000'
  },
  pages: {
    index: {
      entry: 'src/main.js',
      template: 'public/index.html',
      filename: 'index.html'
    }
  }
}
