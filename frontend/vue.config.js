module.exports = {
    filenameHashing: false,
    outputDir: './dist',
    lintOnSave: false,
    devServer: {
        proxy: 'http://localhost:9000'
    }
}
