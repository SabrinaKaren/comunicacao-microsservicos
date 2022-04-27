import UserRepository from '../repository/UserRepository.js';
import * as httpStatus from '../../../config/constants/httpStatus.js';
import UserException from '../exception/UserException.js';

class UserService {

    async findByEmail(req) {
        try {

            const { email } = req.params;
            this.validateRequestData(email);

            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);

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

    validateRequestData(email) {
        if (!email) throw new UserException(httpStatus.BAD_REQUEST, 'User e-mail was not informed.');
    }

    validateUserNotFound(user) {
        if (!user) throw new UserException(httpStatus.BAD_REQUEST, 'User was not found.');
    }

}

export default new UserService();