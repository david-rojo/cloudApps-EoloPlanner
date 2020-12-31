const grpc = require('grpc');
const WeatherService = require('./interface');
const weatherServiceImpl = require('./weatherService');

const server = new grpc.Server();

server.addService(WeatherService.service, weatherServiceImpl);

server.bind('127.0.0.1:8081', grpc.ServerCredentials.createInsecure());

console.log('weather service gRPC server running at http://127.0.0.1:8081');

server.start();