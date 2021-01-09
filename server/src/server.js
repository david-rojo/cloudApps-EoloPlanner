const express = require("express");
const bodyParser = require("body-parser");
const app = express();
const PORT = 3000

app.use(express.static('public'));

const db = require("./app/database");

// drop the table if it already exists
db.sequelize.sync({ force: true }).then(() => {
    console.log("Drop and re-sync db.");
});
//db.sequelize.sync();

// simple route
//app.get("/", (req, res) => {
//  res.json({ message: "Welcome to bezkoder application." });
//});

require("./routes/eoloplant.routes")(app);
// parse requests of content-type - application/json
app.use(bodyParser.json());

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});