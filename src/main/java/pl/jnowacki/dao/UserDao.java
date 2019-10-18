package pl.jnowacki.dao;

import pl.jnowacki.model.User;

public interface UserDao {
    User getUser(String userLogin);
    boolean createUser(String userLogin, String password, String activationToken);
    boolean activateUser(String token);
}
