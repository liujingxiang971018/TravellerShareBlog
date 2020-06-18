package dao.impl;

import dao.userDao;
import domain.collect_route;
import domain.route;
import domain.user;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.Jedis;
import util.JDBCUtils;
import util.JedisPoolUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

public class userDaoImpl implements userDao {
    //申明成员变量jdbcTemplate
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public user findEmail(String email) {
        //定义sql
        String sql="select * from user_info where user_id=?";
        user user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<user>(user.class),email);
            return user;
        } catch (DataAccessException e) {
            System.out.println("查询为空");
            return null;
        }
    }

    @Override
    public boolean createUser(user user) {
        //定义sql，创建用户
        String sql="insert into user_info values(?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUser_id(),user.getUser_password(),user.getUser_id(),null,null,"general_user",null,null,"我很懒，还没有介绍",user.getUser_state(),user.getUser_code());
        return true;
    }

    @Override
    public boolean isFirstLogin(String email) {
        //定义sql
        String sql="select * from user_info where user_id=?";
        user user = template.queryForObject(sql, new BeanPropertyRowMapper<user>(user.class),email);
        String city=user.getUser_city();
        if(city==null || "".equals(city)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean update(user user) {
        //获取email
        String email=user.getUser_id();

        Jedis jedis=JedisPoolUtils.getJedis();

        //定义sql
        if(user.getUser_avatar()!=null && !"".equals(user.getUser_avatar())){
            //更新头像索引
            String sql="update user_info set user_avatar=? where user_id=?";
            template.update(sql,user.getUser_avatar(),email);
            System.out.println("更新成功头像");
        }else if(user.getUser_password()!=null && !"".equals(user.getUser_password())){
            String sql="update user_info set user_password=? where user_id=?";
            template.update(sql,user.getUser_password(),email);
            System.out.println("更新成功密码");
        }else{
            String sql="update user_info set user_nickname=?,user_birthday=?,user_sex=?,user_city=?,user_profile=? where user_id=?";
            template.update(sql,user.getUser_nickname(),user.getUser_birthday(),user.getUser_sex(),user.getUser_city(),user.getUser_profile(),email);
            System.out.println("更新成功基础信息");
        }

        if(user.getUser_password()!=null && !"".equals(user.getUser_password())){
            jedis.hset(email,"password",user.getUser_password());
        }
        if(user.getUser_nickname()!=null && !"".equals(user.getUser_nickname())){
            jedis.hset(email,"nickname",user.getUser_nickname());
        }
        if(user.getUser_sex()!=null && !"".equals(user.getUser_sex())){
            jedis.hset(email,"sex",user.getUser_sex());
        }
        if(user.getUser_city()!=null && !"".equals(user.getUser_city())){
            jedis.hset(email,"city",user.getUser_city());
        }
        if(user.getUser_birthday()!=null && !"".equals(user.getUser_birthday())){
            jedis.hset(email,"birthday",user.getUser_birthday());
        }
        if(user.getUser_profile()!=null && !"".equals(user.getUser_profile())){
            jedis.hset(email,"profile",user.getUser_profile());
        }
        if(user.getUser_avatar()!=null && !"".equals(user.getUser_avatar())){
            jedis.hset(email,"avatar",user.getUser_avatar());
        }
        jedis.close();
        return true;
    }

    @Override
    public boolean delete(String email) {
        Jedis jedis= JedisPoolUtils.getJedis();
        //定义sql
        String sql="delete from user_info where user_id=?";
        template.update(sql,email);

        //删除redis中的数据
        String[] strings={"email","password","nickname","sex","city","birthday","profile","avatar"};
        for(int i=0;i<strings.length;i++){
            jedis.hdel(email,strings[i]);
        }
        jedis.close();
        return true;
    }

}
