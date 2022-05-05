import axios from "axios";
import { PRODUCT_API_URL } from "../../../config/constants/secrets.js";

class ProductClient {

    async checkProducStock(productsData, token, transactionid) {

        try {

            const headers = { Authorization: token, transactionid };
            console.info(`|>>>>> Sending request to Product-API with data ${JSON.stringify(productsData)} | transactionId: ${transactionid}`);
            let response = false;

            await axios
                .post(
                    `${PRODUCT_API_URL}/check-stock`,
                    { products: productsData.products },
                    { headers }
                )
                .then((res) => {
                    console.info(`|----- Success in response from Product-API. TransactionId: ${transactionid}`);
                    response = true;
                })
                .catch((err) => {
                    console.error(`|xxxxx Error in response from Product-API: ${JSON.stringify(err)} | transactionId: ${transactionid}`);
                    response = false;
                });

            return response;

        } catch (err) {
            console.error(`|xxxxx Error when trying to request Product-API: ${JSON.stringify(err)} | transactionId: ${transactionid}`);
            return false;
        }

    }

}

export default new ProductClient();