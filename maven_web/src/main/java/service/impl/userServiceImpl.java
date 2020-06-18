package service.impl;

import dao.impl.userDaoImpl;
import dao.userDao;
import domain.route;
import domain.user;
import service.userService;

import java.util.*;

public class userServiceImpl implements userService {
    private userDao dao=new userDaoImpl();
    @Override
    public user verifyEmailExist(String email) {
        return dao.findEmail(email);
    }

    @Override
    public boolean createUser(user user) {
        return dao.createUser(user);
    }

    @Override
    public boolean isFirstLogin(String email) {
        return dao.isFirstLogin(email);
    }
    //更新用户
    @Override
    public boolean updateUser(user user) {
        return dao.update(user);
    }

    //删除用户
    @Override
    public boolean delUser(String email) {
        return dao.delete(email);
    }


}
