import Order from "../../modules/sales/models/Order.js";
import { v4 as uuidv4 } from "uuid";

export async function createInitialData() {

    console.info("|----- Dropping collection in mongoDB.");
    await Order.collection.drop();

    console.info("|----- Creating initial data in mongoDB");
    await Order.create({
        products: [
            {
                productId: 1000,
                quantity: 2,
            },
            {
                productId: 1001,
                quantity: 1,
            },
            {
                productId: 1002,
                quantity: 1,
            },
        ],
        user: {
            id: "a1sd1as5d165ads1s6",
            name: "Maria",
            email: "maria@email.com",
        },
        status: "APPROVED",
        createdAt: new Date(),
        updatedAt: new Date(),
        transactionid: uuidv4(),
        serviceid: uuidv4()
    });

    await Order.create({
        products: [
            {
                productId: 1000,
                quantity: 4,
            },
            {
                productId: 1001,
                quantity: 2,
            },
        ],
        user: {
            id: "asd1as9d1asd1asd1as5d",
            name: "João",
            email: "joao@email.com",
        },
        status: "REJECTED",
        createdAt: new Date(),
        updatedAt: new Date(),
        transactionid: uuidv4(),
        serviceid: uuidv4()
    });

    let initialData = await Order.find();
    console.info(`|----- Initial data was created: ${JSON.stringify(initialData, undefined, 4)}`);

}