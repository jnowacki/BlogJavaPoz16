package pl.jnowacki.dao;

import pl.jnowacki.model.User;

public interface UserDao {
    User getUser(String userLogin);
}
