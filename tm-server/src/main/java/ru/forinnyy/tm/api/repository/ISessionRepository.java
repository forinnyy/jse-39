// ru/forinnyy/tm/api/repository/ISessionRepository.java
package ru.forinnyy.tm.api.repository;

import lombok.NonNull;
import org.apache.ibatis.annotations.*;
import ru.forinnyy.tm.api.DBConstraints;
import ru.forinnyy.tm.enumerated.Role;
import ru.forinnyy.tm.model.Session;

import java.util.List;

public interface ISessionRepository {

    String T = DBConstraints.TABLE_SESSION;
    String C_ID = DBConstraints.COLUMN_ID;
    String C_USER_ID = DBConstraints.COLUMN_USER_ID;
    String C_DATE = DBConstraints.COLUMN_DATE;
    String C_ROLE = DBConstraints.COLUMN_ROLE;

    @Update(
            "CREATE TABLE IF NOT EXISTS " + T + " (" +
                    C_ID + " UUID PRIMARY KEY, " +
                    C_USER_ID + " UUID NOT NULL, " +
                    C_DATE + " TIMESTAMP WITHOUT TIME ZONE NOT NULL, " +
                    C_ROLE + " VARCHAR(32), " +
                    "FOREIGN KEY (" + C_USER_ID + ") REFERENCES " +
                    DBConstraints.TABLE_USER + "(" + DBConstraints.COLUMN_ID + ") ON DELETE CASCADE" +
                    ")"
    )
    void initTable();

    @Results(id = "SessionMap", value = {
            @Result(column = C_ID,      property = "id"),
            @Result(column = C_USER_ID, property = "userId"),
            @Result(column = C_DATE,    property = "date"),
            @Result(column = C_ROLE,    property = "role", javaType = Role.class)
    })

    @ResultMap("SessionMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    List<Session> findAll(@Param("userId") @NonNull String userId);

    @Insert(
            "INSERT INTO " + T + " (" +
                    C_ID + "," + C_USER_ID + "," + C_DATE + "," + C_ROLE +
                    ") VALUES (" +
                    "#{id}, #{userId}, #{date}, #{role,jdbcType=VARCHAR}" +
                    ")"
    )
    int add(@NonNull Session session);

    @Delete("DELETE FROM " + T + " WHERE " + C_ID + " = #{id}")
    int removeById(@Param("id") @NonNull String id);

    @Select("SELECT EXISTS (SELECT 1 FROM " + T + " WHERE " + C_ID + " = #{id})")
    Boolean existsById(@Param("id") @NonNull String id);

    @Delete("DELETE FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    int clear(@Param("userId") @NonNull String userId);

}
