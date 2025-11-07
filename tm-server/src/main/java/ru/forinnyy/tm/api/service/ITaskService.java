package ru.forinnyy.tm.api.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.exception.entity.AbstractEntityException;
import ru.forinnyy.tm.exception.field.AbstractFieldException;
import ru.forinnyy.tm.exception.user.AbstractUserException;
import ru.forinnyy.tm.model.Task;

import java.util.List;

public interface ITaskService {

    @NonNull
    Task updateById(String userId, String id, String name, String description) throws AbstractFieldException, AbstractEntityException, AbstractUserException;

    @NonNull
    Task updateByIndex(String userId, Integer index, String name, String description) throws AbstractFieldException, AbstractEntityException, AbstractUserException;

    @NonNull
    Task changeTaskStatusById(String userId, String id, Status status) throws AbstractFieldException, AbstractEntityException, AbstractUserException;

    @NonNull
    Task changeTaskStatusByIndex(String userId, Integer index, Status status) throws AbstractFieldException, AbstractEntityException, AbstractUserException;

    @SneakyThrows
    void initTable();

    @NonNull
    List<Task> findAllByProjectId(String userId, String projectId) throws AbstractFieldException;

    @NonNull
    Task create(String userId, String name, String description) throws AbstractFieldException;

    @NonNull
    Task create(String userId, String name) throws AbstractFieldException;

}
