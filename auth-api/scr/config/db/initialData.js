import bcrypt from 'bcrypt';
import User from '../../modules/user/model/User.js';

export async function createInitialData() {
    try {
        await User.sync({ force: true });

        let mariaPasswordCrypted = await bcrypt.hash('123456', 10);
        await User.create({
            name: 'Maria',
            email: 'maria@email.com',
            password: mariaPasswordCrypted
        });

        let joaoPasswordCrypted = await bcrypt.hash('abcdef', 10);
        await User.create({
            name: 'João',
            email: 'joao@email.com',
            password: joaoPasswordCrypted
        });
    } catch (error) {
        console.error(error.message);
    }
}