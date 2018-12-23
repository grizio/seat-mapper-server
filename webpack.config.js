const path = require("path")
const HtmlWebPackPlugin = require("html-webpack-plugin")
const TerserPlugin = require('terser-webpack-plugin');

module.exports = {
  entry: "./src/main/typescript/index.ts",
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: "ts-loader",
        exclude: /node_modules/
      },
      {
        test: /\.html$/,
        use: [
          {
            loader: "html-loader",
            options: {
              minimize: true,
              interpolate: true
            }
          }
        ]
      }
    ]
  },
  resolve: {
    modules: [
      path.resolve('./src/main/typescript'),
      path.resolve('./node_modules')
    ],
    extensions: [".ts", ".tsx", ".js"]
  },
  output: {
    filename: "index.js",
    path: path.resolve(__dirname)
  },
  optimization: {
    minimizer: [new TerserPlugin()]
  },
  plugins: [
    new HtmlWebPackPlugin({
      template: "./src/main/resources/index.html"
    })
  ]
}
