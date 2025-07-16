package com.biaoguoworks;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert user(name) values ('ha11ha');")
    void insertUser();


}