package ru.forinnyy.tm.api.repository;

import lombok.NonNull;
import org.apache.ibatis.annotations.*;
import ru.forinnyy.tm.api.DBConstraints;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.model.Project;

import java.util.List;

public interface IProjectRepository {

    String T = DBConstraints.TABLE_PROJECT;
    String C_ID = DBConstraints.COLUMN_ID;
    String C_NAME = DBConstraints.COLUMN_NAME;
    String C_CREATED = DBConstraints.COLUMN_CREATED;
    String C_DESCRIPTION = DBConstraints.COLUMN_DESCRIPTION;
    String C_USER_ID = DBConstraints.COLUMN_USER_ID;
    String C_STATUS = DBConstraints.COLUMN_STATUS;

    @Update(
            "CREATE TABLE IF NOT EXISTS " + T + " (" +
                    C_ID + " UUID PRIMARY KEY, " +
                    C_NAME + " VARCHAR(255) NOT NULL, " +
                    C_CREATED + " TIMESTAMP WITHOUT TIME ZONE NOT NULL, " +
                    C_DESCRIPTION + " TEXT, " +
                    C_USER_ID + " UUID NOT NULL, " +
                    C_STATUS + " VARCHAR(32) NOT NULL, " +
                    "FOREIGN KEY (" + C_USER_ID + ") REFERENCES " +
                    DBConstraints.TABLE_USER + "(" + DBConstraints.COLUMN_ID + ") ON DELETE CASCADE" +
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

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    List<Project> findAll(@Param("userId") @NonNull String userId);

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId} LIMIT 1")
    Project findOneById(@Param("userId") @NonNull String userId,
                        @Param("id")     @NonNull String id);

    @ResultMap("ProjectMap")
    @Select(
            "SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId} " +
                    "ORDER BY " + C_CREATED + " LIMIT 1 OFFSET #{index}"
    )
    Project findOneByIndex(@Param("userId") @NonNull String userId,
                           @Param("index")  int index);

    @ResultMap("ProjectMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId} ORDER BY ${orderBy}")
    List<Project> findAllSorted(@Param("userId") @NonNull String userId,
                                @Param("orderBy") @NonNull String orderBy);

    @Select("SELECT COUNT(*) FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    int getSize(@Param("userId") @NonNull String userId);


    @Insert(
            "INSERT INTO " + T + " (" +
                    C_ID + "," + C_NAME + "," + C_CREATED + "," +
                    C_DESCRIPTION + "," + C_USER_ID + "," + C_STATUS +
                    ") VALUES (" +
                    "#{id}, #{name}, #{created}, #{description}, #{userId}, #{status}" +
                    ")"
    )
    int add(@NonNull Project p);

    @Update(
            "UPDATE " + T + " SET " +
                    C_NAME + " = #{name}, " +
                    C_DESCRIPTION + " = #{description}, " +
                    C_STATUS + " = #{status} " +
                    "WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId}"
    )
    int update(@NonNull Project p);

    @Delete("DELETE FROM " + T + " WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId}")
    int removeById(@Param("userId") @NonNull String userId,
                   @Param("id")     @NonNull String id);

    @Delete("DELETE FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    int clear(@Param("userId") @NonNull String userId);

}