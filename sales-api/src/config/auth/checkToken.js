import jwt from "jsonwebtoken";
import { promisify } from "util";
import { API_SECRET } from "../secrets/secrets.js";
import { UNAUTHORIZED, INTERNAL_SERVER_ERROR } from "../httpStatus.js";
import AuthException from "./AuthException.js";

export default async (req, res, next) => {

    try {

        const { authorization } = req.headers;
        if (!authorization) throw new AuthException(UNAUTHORIZED, "Access token was not informed.");

        let accessToken = authorization;
        if (accessToken.includes(" ")) accessToken = accessToken.split(" ")[1];

        const decoded = await promisify(jwt.verify)(accessToken, API_SECRET);
        req.authUser = decoded.authUser;
        
        return next();

    } catch (error) {
        const status = error.status ? error.status : INTERNAL_SERVER_ERROR;
        return res.status(status).json({ status, message: error.message });
    }

}