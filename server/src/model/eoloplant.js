module.exports = (sequelize, Sequelize) => {
    const eoloplant = sequelize.define("eoloplant", {
        id: {
            type: Sequelize.INTEGER,
            autoIncrement: true,
            primaryKey: true
        },
        city: {
            type: Sequelize.STRING,
            allowNull: false
        },
        progress: {
            type: Sequelize.INTEGER
        },
        planning: {
            type: Sequelize.STRING
        },
    });

    return eoloplant;
};