package ru.forinnyy.tm.api.service;

import lombok.NonNull;
import ru.forinnyy.tm.enumerated.Sort;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.model.Task;

import java.util.List;

public interface ITaskService {

    void initTable();

    @NonNull List<Task> findAllByProjectId(@NonNull String userId, @NonNull String projectId);

    @NonNull Task create(@NonNull String userId, @NonNull String name);
    @NonNull Task create(@NonNull String userId, @NonNull String name, @NonNull String description);

    @NonNull Task updateById(@NonNull String userId, @NonNull String id,
                             @NonNull String name, @NonNull String description);

    @NonNull Task updateByIndex(@NonNull String userId, @NonNull Integer index,
                                @NonNull String name, @NonNull String description);

    @NonNull Task changeTaskStatusById(@NonNull String userId, @NonNull String id, @NonNull Status status);
    @NonNull Task changeTaskStatusByIndex(@NonNull String userId, @NonNull Integer index, @NonNull Status status);

    @NonNull Task findOneById(@NonNull String userId, @NonNull String id);
    @NonNull Task findOneByIndex(@NonNull String userId, @NonNull Integer index);

    int getSize(@NonNull String userId);

    @NonNull List<Task> findAll(@NonNull String userId);
    @NonNull List<Task> findAll(@NonNull String userId, Sort sort);

    @NonNull List<Task> findAllOrderById(@NonNull String userId);
    @NonNull List<Task> findAllOrderByName(@NonNull String userId);
    @NonNull List<Task> findAllOrderByCreated(@NonNull String userId);

    void clear(@NonNull String userId);

    void removeById(@NonNull String userId, @NonNull String id);

    void removeByIndex(@NonNull String userId, @NonNull Integer index);

}