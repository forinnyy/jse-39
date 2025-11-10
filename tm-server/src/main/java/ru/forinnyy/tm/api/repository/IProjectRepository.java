package ru.forinnyy.tm.api.repository;

import lombok.NonNull;
import org.apache.ibatis.annotations.*;
import ru.forinnyy.tm.api.DBConstraints;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.model.Project;

import java.util.List;

public interface IProjectRepository {

    @NonNull String T_PROJECT     = DBConstraints.TABLE_PROJECT;
    @NonNull String T_USER        = DBConstraints.TABLE_USER;
    @NonNull String C_ID          = DBConstraints.COLUMN_ID;
    @NonNull String C_NAME        = DBConstraints.COLUMN_NAME;
    @NonNull String C_CREATED     = DBConstraints.COLUMN_CREATED;
    @NonNull String C_DESCRIPTION = DBConstraints.COLUMN_DESCRIPTION;
    @NonNull String C_USER_ID     = DBConstraints.COLUMN_USER_ID;
    @NonNull String C_STATUS      = DBConstraints.COLUMN_STATUS;

    @Update(
            "CREATE TABLE IF NOT EXISTS " + T_PROJECT + " (" +
                    C_ID          + " VARCHAR(36) PRIMARY KEY, " +
                    C_NAME        + " VARCHAR(255) NOT NULL, " +
                    C_CREATED     + " TIMESTAMP WITHOUT TIME ZONE NOT NULL, " +
                    C_DESCRIPTION + " TEXT, " +
                    C_USER_ID     + " VARCHAR(36) NOT NULL, " +
                    C_STATUS      + " VARCHAR(32) NOT NULL, " +
                    "FOREIGN KEY (" + C_USER_ID + ") REFERENCES " +
                    T_USER + "(" + C_ID + ") ON DELETE CASCADE" +
                    ")"
    )
    void initTable();

    @Results(id = "ProjectMap", value = {
            @Result(column = C_ID,          property = "id"),
            @Result(column = C_NAME,        property = "name"),
            @Result(column = C_CREATED,     property = "created"),
            @Result(column = C_DESCRIPTION, property = "description"),
            @Result(column = C_USER_ID,     property = "userId"),
            @Result(column = C_STATUS,      property = "status", javaType = Status.class)
    })
    @Select("SELECT * FROM " + T_PROJECT + " WHERE " + C_USER_ID + " = #{userId}")
    List<Project> findAll(@Param("userId") @NonNull String userId);

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T_PROJECT + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_ID)
    List<Project> findAllOrderByIdAsc(@Param("userId") @NonNull String userId);

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T_PROJECT + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_NAME)
    List<Project> findAllOrderByNameAsc(@Param("userId") @NonNull String userId);

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T_PROJECT + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_CREATED)
    List<Project> findAllOrderByCreatedAsc(@Param("userId") @NonNull String userId);

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T_PROJECT + " WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId} LIMIT 1")
    Project findOneById(@Param("userId") @NonNull String userId,
                        @Param("id")     @NonNull String id);

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T_PROJECT + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_CREATED + " LIMIT 1 OFFSET #{index}")
    Project findOneByIndex(@Param("userId") @NonNull String userId,
                           @Param("index")  int index);

    @Select("SELECT COUNT(*) FROM " + T_PROJECT + " WHERE " + C_USER_ID + " = #{userId}")
    int getSize(@Param("userId") @NonNull String userId);

    @Insert(
            "INSERT INTO " + T_PROJECT + " (" +
                    C_ID + "," +
                    C_NAME + "," +
                    C_CREATED + "," +
                    C_DESCRIPTION + "," +
                    C_USER_ID + "," +
                    C_STATUS +
                    ") VALUES (" +
                    "#{id}, #{name}, #{created}, #{description}, #{userId}, #{status}" +
                    ")"
    )
    int add(@NonNull Project p);

    @Update(
            "UPDATE " + T_PROJECT + " SET " +
                    C_NAME + " = #{name}, " +
                    C_DESCRIPTION + " = #{description}, " +
                    C_STATUS + " = #{status} " +
                    "WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId}"
    )
    int update(@NonNull Project p);

    @Delete("DELETE FROM " + T_PROJECT + " WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId}")
    int removeById(@Param("userId") @NonNull String userId,
                   @Param("id")     @NonNull String id);

    @Delete("DELETE FROM " + T_PROJECT + " WHERE " + C_USER_ID + " = #{userId}")
    int clear(@Param("userId") @NonNull String userId);

}