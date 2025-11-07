package ru.forinnyy.tm.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import ru.forinnyy.tm.api.repository.IProjectRepository;
import ru.forinnyy.tm.api.repository.ITaskRepository;
import ru.forinnyy.tm.api.service.IConnectionService;
import ru.forinnyy.tm.api.service.IProjectTaskService;
import ru.forinnyy.tm.exception.entity.ProjectNotFoundException;
import ru.forinnyy.tm.exception.entity.TaskNotFoundException;
import ru.forinnyy.tm.exception.field.ProjectIdEmptyException;
import ru.forinnyy.tm.exception.field.TaskIdEmptyException;
import ru.forinnyy.tm.exception.field.UserIdEmptyException;
import ru.forinnyy.tm.exception.user.PermissionException;
import ru.forinnyy.tm.model.Project;
import ru.forinnyy.tm.model.Task;

public final class ProjectTaskService implements IProjectTaskService {

    @NonNull
    private final IConnectionService connectionService;

    public ProjectTaskService(@NonNull final IConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task bindTaskToProject(
            @NonNull final String userId,
            @NonNull final String projectId,
            @NonNull final String taskId
    ) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (projectId.isEmpty()) throw new ProjectIdEmptyException();
        if (taskId.isEmpty()) throw new TaskIdEmptyException();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository projectRepository = session.getMapper(IProjectRepository.class);
            @NonNull final ITaskRepository taskRepository = session.getMapper(ITaskRepository.class);

            final Project project = projectRepository.findOneById(userId, projectId);
            final Task task = taskRepository.findOneById(userId, taskId);

            if (task == null) throw new TaskNotFoundException();
            if (project == null) throw new ProjectNotFoundException();
            if (!project.getUserId().equals(userId)) throw new PermissionException();
            if (!task.getUserId().equals(userId)) throw new PermissionException();

            task.setProjectId(projectId);
            taskRepository.update(task);

            session.commit();
            return task;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Task unbindTaskFromProject(
            @NonNull final String userId,
            @NonNull final String projectId,
            @NonNull final String taskId
    ) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (projectId.isEmpty()) throw new ProjectIdEmptyException();
        if (taskId.isEmpty()) throw new TaskIdEmptyException();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository projectRepository = session.getMapper(IProjectRepository.class);
            @NonNull final ITaskRepository taskRepository = session.getMapper(ITaskRepository.class);

            final Project project = projectRepository.findOneById(userId, projectId);
            if (project == null) throw new ProjectNotFoundException();
            if (!project.getUserId().equals(userId)) throw new PermissionException();

            final Task task = taskRepository.findOneById(userId, taskId);
            if (task == null) throw new TaskNotFoundException();
            if (!task.getUserId().equals(userId)) throw new PermissionException();

            task.setProjectId(null);
            taskRepository.update(task);

            session.commit();
            return task;
        }
    }

    @Override
    @SneakyThrows
    public void removeProjectById(
            @NonNull final String userId,
            @NonNull final String projectId
    ) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (projectId.isEmpty()) throw new ProjectIdEmptyException();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository projectRepository = session.getMapper(IProjectRepository.class);
            @NonNull final ITaskRepository taskRepository = session.getMapper(ITaskRepository.class);

            final Project project = projectRepository.findOneById(userId, projectId);
            if (project == null) throw new ProjectNotFoundException();
            if (!project.getUserId().equals(userId)) throw new PermissionException();

            taskRepository.removeAllByProjectId(userId, projectId);
            projectRepository.removeById(userId, projectId);

            session.commit();
        }
    }

}