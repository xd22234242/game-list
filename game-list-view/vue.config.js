const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: './',
  // assertDir: 'static',
  productionSourceMap: false,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9001',
        changeOrigin: true,
        pathRewrite: {
          '/api': ''
        }
      }
    }
  }
})

