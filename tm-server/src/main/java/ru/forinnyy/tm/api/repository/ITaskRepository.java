package ru.forinnyy.tm.api.repository;

import lombok.NonNull;
import org.apache.ibatis.annotations.*;
import ru.forinnyy.tm.api.DBConstraints;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.model.Task;

import java.util.List;

public interface ITaskRepository {

    String T             = DBConstraints.TABLE_TASK;
    String C_ID          = DBConstraints.COLUMN_ID;
    String C_NAME        = DBConstraints.COLUMN_NAME;
    String C_CREATED     = DBConstraints.COLUMN_CREATED;
    String C_DESCRIPTION = DBConstraints.COLUMN_DESCRIPTION;
    String C_USER_ID     = DBConstraints.COLUMN_USER_ID;
    String C_STATUS      = DBConstraints.COLUMN_STATUS;
    String C_PROJECT_ID  = DBConstraints.COLUMN_PROJECT_ID;

    @Update(
            "CREATE TABLE IF NOT EXISTS " + T + " (" +
                    C_ID          + " VARCHAR(36) PRIMARY KEY, " +
                    C_NAME        + " VARCHAR(255) NOT NULL, " +
                    C_CREATED     + " TIMESTAMP WITHOUT TIME ZONE NOT NULL, " +
                    C_DESCRIPTION + " TEXT, " +
                    C_USER_ID     + " VARCHAR(36) NOT NULL, " +
                    C_STATUS      + " VARCHAR(32) NOT NULL, " +
                    C_PROJECT_ID  + " VARCHAR(36), " +
                    "FOREIGN KEY (" + C_USER_ID + ") REFERENCES " + DBConstraints.TABLE_USER + "(" + DBConstraints.COLUMN_ID + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY (" + C_PROJECT_ID + ") REFERENCES " + DBConstraints.TABLE_PROJECT + "(" + DBConstraints.COLUMN_ID + ") ON DELETE CASCADE" +
                    ")"
    )
    void initTable();

    @Results(id = "TaskMap", value = {
            @Result(column = C_ID,          property = "id"),
            @Result(column = C_NAME,        property = "name"),
            @Result(column = C_CREATED,     property = "created"),
            @Result(column = C_DESCRIPTION, property = "description"),
            @Result(column = C_USER_ID,     property = "userId"),
            @Result(column = C_STATUS,      property = "status", javaType = Status.class),
            @Result(column = C_PROJECT_ID,  property = "projectId")
    })
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    List<Task> findAll(@Param("userId") @NonNull String userId);

    @ResultMap("TaskMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_ID)
    List<Task> findAllOrderByIdAsc(@Param("userId") @NonNull String userId);

    @ResultMap("TaskMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_NAME)
    List<Task> findAllOrderByNameAsc(@Param("userId") @NonNull String userId);

    @ResultMap("TaskMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_CREATED)
    List<Task> findAllOrderByCreatedAsc(@Param("userId") @NonNull String userId);

    @ResultMap("TaskMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId} AND " + C_PROJECT_ID + " = #{projectId}")
    List<Task> findAllByProjectId(@Param("userId") @NonNull String userId,
                                  @Param("projectId") @NonNull String projectId);

    @ResultMap("TaskMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId} LIMIT 1")
    Task findOneById(@Param("userId") @NonNull String userId,
                     @Param("id")     @NonNull String id);

    @ResultMap("TaskMap")
    @Select("SELECT * FROM " + T + " WHERE " + C_USER_ID + " = #{userId} ORDER BY " + C_CREATED + " LIMIT 1 OFFSET #{index}")
    Task findOneByIndex(@Param("userId") @NonNull String userId,
                        @Param("index")  int index);

    @Select("SELECT COUNT(*) FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    int getSize(@Param("userId") @NonNull String userId);

    @Insert(
            "INSERT INTO " + T + " (" +
                    C_ID + "," +
                    C_NAME + "," +
                    C_CREATED + "," +
                    C_DESCRIPTION + "," +
                    C_USER_ID + "," +
                    C_STATUS + "," +
                    C_PROJECT_ID +
                    ") VALUES (" +
                    "#{id}, #{name}, #{created}, #{description}, #{userId}, #{status}, #{projectId}" +
                    ")"
    )
    int add(@NonNull Task t);

    @Update(
            "UPDATE " + T + " SET " +
                    C_NAME + " = #{name}, " +
                    C_DESCRIPTION + " = #{description}, " +
                    C_STATUS + " = #{status}, " +
                    C_PROJECT_ID + " = #{projectId} " +
                    "WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId}"
    )
    int update(@NonNull Task t);

    @Delete("DELETE FROM " + T + " WHERE " + C_ID + " = #{id} AND " + C_USER_ID + " = #{userId}")
    int removeById(@Param("userId") @NonNull String userId,
                   @Param("id")     @NonNull String id);

    @Delete("DELETE FROM " + T + " WHERE " + C_USER_ID + " = #{userId}")
    int clear(@Param("userId") @NonNull String userId);

    @Delete("DELETE FROM " + T + " WHERE " + C_USER_ID + " = #{userId} AND " + C_PROJECT_ID + " = #{projectId}")
    int removeAllByProjectId(@Param("userId") @NonNull String userId,
                             @Param("projectId") @NonNull String projectId);

}