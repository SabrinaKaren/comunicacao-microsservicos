import mongoose from "mongoose";
import { MONGO_DB_URL } from "../secrets/secrets.js";

export function connect() {
    mongoose.connect(MONGO_DB_URL, {
        useNewUrlParser: true
    });
    mongoose.connection.on("connected", function() {
        console.info("Application successfully connected to MongoDB.");
    });
    mongoose.connection.on("error", function() {
        console.error("Application was not successfully connected to MongoDB.");
    });
}