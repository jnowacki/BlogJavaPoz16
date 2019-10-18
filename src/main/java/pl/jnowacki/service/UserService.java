package pl.jnowacki.service;

public interface UserService {
    boolean isUserValid(String login, String password);
    void registerUser(String login, String password, String contextPath);
    boolean activateUser(String token);
}
