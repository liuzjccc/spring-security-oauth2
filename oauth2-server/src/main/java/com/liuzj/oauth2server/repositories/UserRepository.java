package com.liuzj.oauth2server.repositories;

import com.liuzj.oauth2server.domain.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Integer>, CrudRepository<User, Integer> {

    @Select("select * from user where name = #{userName}")
    User findByUserName(String userName);

}