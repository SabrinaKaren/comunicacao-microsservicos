import UserService from '../service/UserService.js';

class UserController {

    async findByEmail(req, res) {
        let user = await UserService.findByEmail(req);
        return res.status(user.status).json(user);
    }

}

export default new UserController();