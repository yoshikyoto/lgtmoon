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
    },
    en: {
      entry: 'src/main.js',
      template: 'public/en.html',
      filename: 'en.html'
    }
  },
  pluginOptions: {
    i18n: {
      locale: 'ja',
      fallbackLocale: 'ja',
      localeDir: 'locales',
      enableInSFC: true
    }
  }
}
