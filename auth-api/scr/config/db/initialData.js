import bcrypt from 'bcrypt';
import User from '../../modules/user/model/User';

export async function createInitialData() {
    try {
        await User.sync({ force: true });

        let passwordCrypted = await bcrypt.hash('123456', 10);

        await User.create({
            name: 'Usuário inicial',
            email: 'usuario.inicial@email.com',
            password: passwordCrypted
        });
    } catch (err) {
        console.error(err);
    }
}