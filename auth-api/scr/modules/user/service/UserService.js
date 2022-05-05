import UserRepository from "../repository/UserRepository.js";
import * as httpStatus from "../../../config/constants/httpStatus.js";
import UserException from "../exception/UserException.js";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import * as secrets from "../../../config/constants/secrets.js";

class UserService {

    async getAccessToken(req) {
        try {

            // log
            const { transactionid, serviceid } = req.headers;
            console.info(
                `|>>>>> Request to POST login with data ${JSON.stringify(req.body)} | transactionId: ${transactionid} | serviceId: ${serviceid}`
            );

            const { email, password } = req.body;
            this.validateAccessTokenData(email, password);

            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            await this.validatePassword(password, user.password);

            const authUser = {
                id: user.id,
                name: user.name,
                email: user.email
            }

            const accessToken = jwt.sign(
                { authUser },
                secrets.API_SECRET,
                { expiresIn: "1d"}
            );

            let response = {
                status: httpStatus.SUCCESS,
                accessToken
            };

            // log
            console.info(
                `|<<<<< Response to POST login with data ${JSON.stringify(response)} | transactionId: ${transactionid} | serviceId: ${serviceid}`
            );

            return response;

        } catch (error) {
            return {
                status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: error.message
            };            
        }
    }

    async findByEmail(req) {
        try {

            const { email } = req.params;
            const { authUser } = req;
            this.validateRequestData(email);

            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            this.validateAuthenticatedUser(user, authUser);

            return {
                status: httpStatus.SUCCESS,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email
                }
            };

        } catch (error) {
            return {
                status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: error.message
            };
        }
    }

    // validadores

    validateAccessTokenData(email, password) {
        if (!email || !password) throw new UserException(httpStatus.UNAUTHORIZED, "Email and password must be informe.");
    }

    async validatePassword(password, passwordCrypted) {
        if (!(await bcrypt.compare(password, passwordCrypted))) {
            throw new UserException(httpStatus.UNAUTHORIZED, "Password does not match.");
        }
    }

    validateRequestData(email) {
        if (!email) throw new UserException(httpStatus.BAD_REQUEST, "User e-mail was not informed.");
    }

    validateUserNotFound(user) {
        if (!user) throw new UserException(httpStatus.BAD_REQUEST, "User was not found.");
    }

    validateAuthenticatedUser(user, authenticatedUser) {
        if (!authenticatedUser || authenticatedUser.id !== user.id) throw new UserException(httpStatus.FORBIDDEN, "You cannot see this user data.");
    }

}

export default new UserService();