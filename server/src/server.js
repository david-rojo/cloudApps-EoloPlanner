let express = require("express");
let bodyParser = require("body-parser");
let app = express();
let expressWs = require('express-ws')(app);
const PORT = 3000

const db = require("./app/database");
const broker = require("./app/broker");

// drop the table if it already exists
db.sequelize.sync({ force: true }).then(() => {
    console.log("Drop and re-sync db.");
});
//db.sequelize.sync();
broker.init();

app.get('/test', function (req, res, next) {
  console.log('/test endpoint executed');
  res.json({message:"Test response"});
  res.end();
});

const websockets = {}
app.ws('/notifications', function (ws, req) {
  ws.id = uuid.v4();
  console.log('User ${ws.id} connected');
  websockets[ws.id] = ws;
  console.log(`User ${ws.id} connected`);
  ws.send(JSON.stringify({socketId: ws.id}))
  
  ws.on('message', function (msg) {
      console.log('Message received:' + msg);
  });

});

require("./routes/eoloplant.routes")(app);
// parse requests of content-type - application/json
app.use(bodyParser.json());
app.use(express.static('public'));

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});