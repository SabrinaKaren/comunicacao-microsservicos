import mongoose from "mongoose";
import { MONGO_DB_URL } from "../constants/secrets.js";

export function connectMongoDb() {
    console.log("----------| mongoDbConfig.connectMongoDb()");
    mongoose.connect(MONGO_DB_URL, {
        useNewUrlParser: true,
        serverSelectionTimeoutMS: 180000
    });
    mongoose.connection.on("connected", function() {
        console.info("----------| Application successfully connected to MongoDB.");
    });
    mongoose.connection.on("error", function() {
        console.error("----------| Application was not successfully connected to MongoDB.");
    });
}