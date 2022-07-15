package com.tang.netty.mapper;

import java.sql.SQLException;
import java.util.List;

public interface UserMapper {

    User findById(int id) throws SQLException;

    boolean addUser(User user) throws SQLException;

    List<User> findAll() throws SQLException;
}
