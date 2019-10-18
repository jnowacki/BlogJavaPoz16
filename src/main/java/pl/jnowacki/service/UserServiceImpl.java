package pl.jnowacki.service;

import org.apache.commons.lang3.RandomStringUtils;
import pl.jnowacki.dao.UserDao;
import pl.jnowacki.dao.UserDaoImpl;
import pl.jnowacki.model.User;
import pl.jnowacki.util.EmailUtil;
import pl.jnowacki.util.PasswordUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean isUserValid(String login, String password) {
        User user = userDao.getUser(login);

        return user != null &&
                login.equals(user.getUsername()) &&
                PasswordUtil.checkPassword(password, user.getPassword()) &&
                user.isActive();
    }

    @Override
    public void registerUser(String login, String password, String contextPath) {
        int tokenLength = 50;
        String token = RandomStringUtils.randomAlphanumeric(tokenLength);

        if (userDao.createUser(login, PasswordUtil.hashPassword(password), token)) {
            sendActivationEmail(login, token, contextPath);
        }
    }

    @Override
    public boolean activateUser(String token) {
        return userDao.activateUser(token);
    }

    private void sendActivationEmail(String email, String token, String contextPath){
        EmailUtil.sendActivationEmail(email, token, contextPath);
    }
}
