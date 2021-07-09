const proxy = require("http-proxy-middleware");

module.exports = (app) => {
  app.use(
    ["/api", "/web", "/monitoring/api"],
    proxy({
      target: "http://localhost:52000",
      changeOrigin: true,
    })
  );
};
