package com.tang.netty.mapper;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 * @date 2021-06-03 20:37
 * @author tangwenkai
 */
public class UserMapperService
{

    private static final UserMapperService instance = new UserMapperService();

    /**
     * 获取单例实例
     * @return
     */
    public static UserMapperService getInstance(){
        return instance;
    }

    private UserMapperService(){}

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     * @throws SQLException
     */
    public final User getUserById(int id) throws SQLException {
        SqlSession session = null;
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-conf.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
            session = sqlSessionFactory.openSession();
            //查询
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.findById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return new User();
    }

    public static void main(String[] args) throws SQLException {
        User user = UserMapperService.getInstance().getUserById(5);
        System.out.println(user);
    }
}
