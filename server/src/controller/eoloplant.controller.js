const db = require("../app/database");
const Eoloplant = db.eoloplants;
const Op = db.Sequelize.Op;

// Create and Save a new EoloPlant
exports.create = (req, res) => {
    // Validate request
    if (!req.body.city) {
        res.status(400).send({
            message: "request must include a city!"
        });
        return;
    }

    // Create a Eoloplant
    const eoloplant = {
        city: req.body.city,
    };

    // Save Eoloplant in the database
    Eoloplant.create(eoloplant)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message: err.message || "Some error occurred while creating the Eoloplant."
            });
        });
};

// Find a single Eoloplant with an id
exports.findOne = (req, res) => {
    const id = req.params.id;

    Eoloplant.findByPk(id)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message: "Error retrieving Eoloplant with id=" + id
            });
        });
};

// Retrieve all EoloPlants from the database.
exports.findAll = (req, res) => {
    Eoloplant.findAll()
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message: err.message || "Some error occurred while retrieving EoloPlants."
            });
        });
};

// Update a Tutorial by the id in the request
exports.update = (req, res) => {
    const id = req.params.id;

    Eoloplant.update(req.body, {
            where: {
                id: id
            }
        })
        .then(num => {
            if (num == 1) {
                res.send({
                    message: "Eoloplant was updated successfully."
                });
            } else {
                res.send({
                    message: `Cannot update Tutorial with id=${id}. Maybe Eoloplant was not found or req.body is empty!`
                });
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Error updating Eoloplant with id=" + id
            });
        });
};

// Delete a EoloPlants with the specified id in the request
exports.delete = (req, res) => {
    const id = req.params.id;

    Eoloplant.destroy({
            where: {
                id: id
            }
        })
        .then(num => {
            if (num == 1) {
                res.send({
                    message: "EoloPlant was deleted successfully!"
                });
            } else {
                res.send({
                    message: `Cannot delete EoloPlant with id=${id}. Maybe EoloPlant was not found!`
                });
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Could not delete EoloPlant with id=" + id
            });
        });
};