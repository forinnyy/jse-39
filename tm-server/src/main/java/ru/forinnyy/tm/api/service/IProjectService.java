package ru.forinnyy.tm.api.service;

import lombok.NonNull;
import ru.forinnyy.tm.enumerated.Sort;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.model.Project;

import java.util.List;

public interface IProjectService {

    void initTable();

    @NonNull Project create(@NonNull String userId, @NonNull String name);

    @NonNull Project create(@NonNull String userId, @NonNull String name, @NonNull String description);

    @NonNull Project updateById(@NonNull String userId, @NonNull String id,
                                @NonNull String name, @NonNull String description);

    @NonNull Project updateByIndex(@NonNull String userId, @NonNull Integer index,
                                   @NonNull String name, @NonNull String description);

    @NonNull Project changeProjectStatusById(@NonNull String userId, @NonNull String id, @NonNull Status status);

    @NonNull Project changeProjectStatusByIndex(@NonNull String userId, @NonNull Integer index, @NonNull Status status);

    @NonNull Project findOneById(@NonNull String userId, @NonNull String id);
    @NonNull Project findOneByIndex(@NonNull String userId, @NonNull Integer index);

    int getSize(@NonNull String userId);

    @NonNull List<Project> findAll(@NonNull String userId);

    @NonNull List<Project> findAll(@NonNull String userId, Sort sort);

}