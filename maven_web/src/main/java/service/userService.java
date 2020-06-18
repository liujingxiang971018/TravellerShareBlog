package service;

import domain.route;
import domain.user;

import java.util.List;

public interface userService {
    public user verifyEmailExist(String email);

    boolean createUser(user user);

    boolean isFirstLogin(String email);

    boolean updateUser(user user);

    boolean delUser(String email);

}
