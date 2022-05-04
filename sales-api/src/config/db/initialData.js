import Order from "../../modules/sales/models/Order.js";

export async function createInitialData() {

    console.log("----------| initialData.createInitialData() - antes do drop");
    await Order.collection.drop();
    console.log("----------| initialData.createInitialData() - depois do drop");

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
        updatedAt: new Date()
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
        updatedAt: new Date()
    });

    let initialData = await Order.find();
    console.info(`----------| Initial data was created: ${JSON.stringify(initialData, undefined, 4)}`);
    console.log("----------| initialData.createInitialData() - depois do Order.find()");

}