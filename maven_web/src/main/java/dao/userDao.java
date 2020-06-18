package dao;

import domain.route;
import domain.user;

import java.util.List;
import java.util.Map;

public interface userDao {
    //查询指定email是否存在
    public user findEmail(String email);

    boolean createUser(user user);

    boolean isFirstLogin(String email);

    boolean update(user user);

    boolean delete(String email);

}
