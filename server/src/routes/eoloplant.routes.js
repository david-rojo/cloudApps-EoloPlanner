module.exports = app => {
    const eoloplants = require("../controller/eoloplant.controller.js");

    var router = require("express").Router();

    // Create a new Eoloplant
    router.post("/", eoloplants.create);

    // Retrieve all Eoloplants
    router.get("/", eoloplants.findAll);

    // Retrieve a single Eoloplant with id
    router.get("/:id", eoloplants.findOne);

    // Update a Eoloplant with id
    router.put("/:id", eoloplants.update);

    // Delete a Eoloplant with id
    router.delete("/:id", eoloplants.delete);

    app.use('/api/eoloplants', router);
}