const amqp = require('amqplib/callback_api');
const db = require('./database');
const CONN_URL = 'amqp://guest:guest@localhost';
const createQueue = 'eoloplantCreationRequests';
const progressQueue = 'eoloplantCreationProgressNotifications';

let creationChannel = null;
let progressChannel = null;

async function init(){
    amqp.connect(CONN_URL, async function (error, connection) {
        if (error) {
            throw error;
        }
    
        creationChannel = await connection.createChannel(function (creationError, channel) {
            if (creationError)
                throw creationError;
            channel.assertQueue(createQueue, {
                durable: false
            });
        });
    
        progressChannel = await connection.createChannel(function (progressError, channel) {
            if (progressError) {
                throw progressError;
            }
            channel.assertQueue(progressQueue, {
                durable: false
            });
            channel.consume(progressQueue, function (msg) {
                console.log("Received:", msg.content.toString());
                let progress = JSON.parse(msg.content.toString());
                //TODO refresh info in clients
                updatePlantProgress(JSON.parse(msg.content))
            }, {
                noAck: true
            });
        });
    });
}

process.on('exit', (code) => {
    creationChannel.close();
    progressChannel.close();
    console.log(`Closing RabbitMQ channels`);
});

const publishToQueue = async (data) => {
    creationChannel.sendToQueue(createQueue, Buffer.from(data));
}

function updatePlantProgress(progressInfo) {
    db.Plant.update({
        progress: plantInfo.progress
    }, {
        where: {
            id: plantInfo.id
        }
    })
}

module.exports.publishToQueue = publishToQueue;
module.exports.init = init;