package ru.forinnyy.tm.api.repository;

import lombok.NonNull;
import org.apache.ibatis.annotations.*;
import ru.forinnyy.tm.api.DBConstraints;
import ru.forinnyy.tm.enumerated.Role;
import ru.forinnyy.tm.model.User;

import java.util.List;

public interface IUserRepository {

    @NonNull String T_USER        = DBConstraints.TABLE_USER;
    @NonNull String C_ID          = DBConstraints.COLUMN_ID;
    @NonNull String C_LOGIN       = DBConstraints.COLUMN_LOGIN;
    @NonNull String C_PASSWORD    = DBConstraints.COLUMN_PASSWORD;
    @NonNull String C_EMAIL       = DBConstraints.COLUMN_EMAIL;
    @NonNull String C_LOCKED      = DBConstraints.COLUMN_LOCKED;
    @NonNull String C_FIRST_NAME  = DBConstraints.COLUMN_FIRST_NAME;
    @NonNull String C_LAST_NAME   = DBConstraints.COLUMN_LAST_NAME;
    @NonNull String C_MIDDLE_NAME = DBConstraints.COLUMN_MIDDLE_NAME;
    @NonNull String C_ROLE        = DBConstraints.COLUMN_ROLE;
    @NonNull String C_CREATED     = DBConstraints.COLUMN_CREATED;

    @Update(
            "CREATE TABLE IF NOT EXISTS " + T_USER + " (" +
                    C_ID          + " VARCHAR(36) PRIMARY KEY, " +
                    C_LOGIN       + " VARCHAR(255) NOT NULL UNIQUE, " +
                    C_PASSWORD    + " VARCHAR(255) NOT NULL, " +
                    C_EMAIL       + " VARCHAR(255) UNIQUE, " +
                    C_LOCKED      + " BOOLEAN NOT NULL DEFAULT FALSE, " +
                    C_FIRST_NAME  + " VARCHAR(255), " +
                    C_LAST_NAME   + " VARCHAR(255), " +
                    C_MIDDLE_NAME + " VARCHAR(255), " +
                    C_ROLE        + " VARCHAR(50) NOT NULL DEFAULT 'USUAL'" +
                    ")"
    )
    void initTable();

    @Results(id = "UserMap", value = {
            @Result(column = C_ID,          property = "id"),
            @Result(column = C_LOGIN,       property = "login"),
            @Result(column = C_PASSWORD,    property = "passwordHash"),
            @Result(column = C_EMAIL,       property = "email"),
            @Result(column = C_LOCKED,      property = "locked"),
            @Result(column = C_FIRST_NAME,  property = "firstName"),
            @Result(column = C_LAST_NAME,   property = "lastName"),
            @Result(column = C_MIDDLE_NAME, property = "middleName"),
            @Result(column = C_ROLE,        property = "role", javaType = Role.class)
    })
    @Select("SELECT * FROM " + T_USER)
    List<User> findAll();

    @ResultMap("UserMap")
    @Select("SELECT * FROM " + T_USER + " WHERE " + C_ID + " = #{id} LIMIT 1")
    User findOneById(@Param("id") @NonNull String id);

    @ResultMap("UserMap")
    @Select("SELECT * FROM " + T_USER + " WHERE " + C_LOGIN + " = #{login} LIMIT 1")
    User findByLogin(@Param("login") @NonNull String login);

    @ResultMap("UserMap")
    @Select("SELECT * FROM " + T_USER + " WHERE " + C_EMAIL + " = #{email} LIMIT 1")
    User findByEmail(@Param("email") @NonNull String email);

    @Select("SELECT EXISTS(SELECT 1 FROM " + T_USER + " WHERE " + C_LOGIN + " = #{login})")
    Boolean isLoginExist(@Param("login") @NonNull String login);

    @Select("SELECT EXISTS(SELECT 1 FROM " + T_USER + " WHERE " + C_EMAIL + " = #{email})")
    Boolean isEmailExist(@Param("email") @NonNull String email);

    @Insert(
            "INSERT INTO " + T_USER + " (" +
                    C_ID + "," +
                    C_LOGIN + "," +
                    C_PASSWORD + "," +
                    C_FIRST_NAME + "," +
                    C_LAST_NAME + "," +
                    C_MIDDLE_NAME + "," +
                    C_EMAIL + "," +
                    C_ROLE + "," +
                    C_LOCKED +
                    ") VALUES (" +
                    "#{id}, #{login}, #{passwordHash}, " +
                    "#{firstName}, #{lastName}, #{middleName}, " +
                    "#{email}, #{role}, #{locked}" +
                    ")"
    )
    int add(@NonNull User user);

    @Update(
            "UPDATE " + T_USER + " SET " +
                    C_PASSWORD    + " = #{passwordHash}, " +
                    C_FIRST_NAME  + " = #{firstName}, " +
                    C_LAST_NAME   + " = #{lastName}, " +
                    C_MIDDLE_NAME + " = #{middleName}, " +
                    C_EMAIL       + " = #{email}, " +
                    C_ROLE        + " = #{role}, " +
                    C_LOCKED      + " = #{locked} " +
                    "WHERE " + C_ID + " = #{id}"
    )
    int update(@NonNull User user);

    @Delete("DELETE FROM " + T_USER + " WHERE " + C_ID + " = #{id}")
    int removeById(@Param("id") @NonNull String id);

}