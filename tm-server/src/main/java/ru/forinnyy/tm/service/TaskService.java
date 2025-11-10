package ru.forinnyy.tm.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import ru.forinnyy.tm.api.repository.ITaskRepository;
import ru.forinnyy.tm.api.service.IConnectionService;
import ru.forinnyy.tm.api.service.ITaskService;
import ru.forinnyy.tm.enumerated.Sort;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.exception.entity.TaskNotFoundException;
import ru.forinnyy.tm.exception.field.*;
import ru.forinnyy.tm.exception.user.PermissionException;
import ru.forinnyy.tm.model.Task;

import java.util.Collections;
import java.util.List;

public final class TaskService implements ITaskService {

    @NonNull
    private final IConnectionService connectionService;

    public TaskService(@NonNull final IConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    @SneakyThrows
    public void initTable() {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            session.getMapper(ITaskRepository.class).initTable();
            session.commit();
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Task> findAllByProjectId(@NonNull final String userId, @NonNull final String projectId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (projectId.isEmpty()) return Collections.emptyList();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            return session.getMapper(ITaskRepository.class).findAllByProjectId(userId, projectId);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task create(@NonNull final String userId, @NonNull final String name, @NonNull final String description) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        if (description.isEmpty()) throw new DescriptionEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            @NonNull final Task task = new Task();
            task.setUserId(userId);
            task.setName(name);
            task.setDescription(description);

            repository.add(task);
            session.commit();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task create(@NonNull final String userId, @NonNull final String name) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            @NonNull final Task task = new Task();
            task.setUserId(userId);
            task.setName(name);

            repository.add(task);
            session.commit();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task updateById(@NonNull final String userId, @NonNull final String id,
                           @NonNull final String name, @NonNull final String description) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (id.isEmpty()) throw new TaskIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        if (description.isEmpty()) throw new DescriptionEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            final Task task = repository.findOneById(userId, id);
            if (task == null) throw new TaskNotFoundException();
            if (!task.getUserId().equals(userId)) throw new PermissionException();
            task.setName(name);
            task.setDescription(description);

            repository.update(task);
            session.commit();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task updateByIndex(@NonNull final String userId, @NonNull final Integer index,
                              @NonNull final String name, @NonNull final String description) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        if (description.isEmpty()) throw new DescriptionEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            final int size = repository.getSize(userId);
            if (index == null || index < 0 || index >= size) throw new IndexIncorrectException();
            final Task task = repository.findOneByIndex(userId, index);
            if (task == null) throw new TaskNotFoundException();
            if (!task.getUserId().equals(userId)) throw new PermissionException();
            task.setName(name);
            task.setDescription(description);

            repository.update(task);
            session.commit();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task changeTaskStatusById(@NonNull final String userId, @NonNull final String id, @NonNull final Status status) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (id.isEmpty()) throw new TaskIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            final Task task = repository.findOneById(userId, id);
            if (task == null) throw new TaskNotFoundException();
            if (!task.getUserId().equals(userId)) throw new PermissionException();
            task.setStatus(status);

            repository.update(task);
            session.commit();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task changeTaskStatusByIndex(@NonNull final String userId, @NonNull final Integer index, @NonNull final Status status) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            final int size = repository.getSize(userId);
            if (index == null || index < 0 || index >= size) throw new IndexIncorrectException();
            final Task task = repository.findOneByIndex(userId, index);
            if (task == null) throw new TaskNotFoundException();
            if (!task.getUserId().equals(userId)) throw new PermissionException();
            task.setStatus(status);

            repository.update(task);
            session.commit();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task findOneById(@NonNull final String userId, @NonNull final String id) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (id.isEmpty()) throw new TaskIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final Task task = session.getMapper(ITaskRepository.class).findOneById(userId, id);
            if (task == null) throw new TaskNotFoundException();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task findOneByIndex(@NonNull final String userId, @NonNull final Integer index) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            final int size = repository.getSize(userId);
            if (index == null || index < 0 || index >= size) throw new IndexIncorrectException();
            final Task task = repository.findOneByIndex(userId, index);
            if (task == null) throw new TaskNotFoundException();
            return task;
        }
    }

    @Override
    @SneakyThrows
    public int getSize(@NonNull final String userId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            return session.getMapper(ITaskRepository.class).getSize(userId);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Task> findAll(@NonNull final String userId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            return session.getMapper(ITaskRepository.class).findAll(userId);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Task> findAll(@NonNull final String userId, final Sort sort) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            if (sort == null) return repository.findAll(userId);
            if (sort == Sort.BY_NAME) return repository.findAllOrderByNameAsc(userId);
            if (sort == Sort.BY_CREATED) return repository.findAllOrderByCreatedAsc(userId);
            return repository.findAllOrderByIdAsc(userId);
        }
    }

    @Override
    @SneakyThrows
    public void clear(@NonNull final String userId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            session.getMapper(ITaskRepository.class).clear(userId);
            session.commit();
        }
    }

    @Override
    @SneakyThrows
    public void removeById(@NonNull final String userId, @NonNull final String id) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (id.isEmpty()) throw new TaskIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            session.getMapper(ITaskRepository.class).removeById(userId, id);
            session.commit();
        }
    }

    @Override
    @SneakyThrows
    public void removeByIndex(@NonNull final String userId, @NonNull final Integer index) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final ITaskRepository repository = session.getMapper(ITaskRepository.class);
            final int size = repository.getSize(userId);
            if (index == null || index < 0 || index >= size) throw new IndexIncorrectException();
            final Task t = repository.findOneByIndex(userId, index);
            if (t == null) throw new TaskNotFoundException();
            repository.removeById(userId, t.getId());
            session.commit();
        }
    }

}