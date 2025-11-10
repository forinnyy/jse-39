package ru.forinnyy.tm.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import ru.forinnyy.tm.api.repository.IProjectRepository;
import ru.forinnyy.tm.api.service.IConnectionService;
import ru.forinnyy.tm.api.service.IProjectService;
import ru.forinnyy.tm.enumerated.Sort;
import ru.forinnyy.tm.enumerated.Status;
import ru.forinnyy.tm.exception.entity.ProjectNotFoundException;
import ru.forinnyy.tm.exception.field.*;
import ru.forinnyy.tm.exception.user.PermissionException;
import ru.forinnyy.tm.model.Project;

import java.util.List;

public final class ProjectService implements IProjectService {

    @NonNull
    private final IConnectionService connectionService;

    public ProjectService(@NonNull final IConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    @SneakyThrows
    public void initTable() {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            repository.initTable();
            session.commit();
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project create(@NonNull final String userId, @NonNull final String name) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            @NonNull final Project project = new Project();
            project.setUserId(userId);
            project.setName(name);

            repository.add(project);
            session.commit();
            return project;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project create(@NonNull final String userId, @NonNull final String name, @NonNull final String description) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        if (description.isEmpty()) throw new DescriptionEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            @NonNull final Project project = new Project();
            project.setUserId(userId);
            project.setName(name);
            project.setDescription(description);

            repository.add(project);
            session.commit();
            return project;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project updateById(@NonNull final String userId, @NonNull final String id,
                              @NonNull final String name, @NonNull final String description) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (id.isEmpty()) throw new ProjectIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        if (description.isEmpty()) throw new DescriptionEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            final Project project = repository.findOneById(userId, id);
            if (project == null) throw new ProjectNotFoundException();
            if (!project.getUserId().equals(userId)) throw new PermissionException();
            project.setName(name);
            project.setDescription(description);

            repository.update(project);
            session.commit();
            return project;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project updateByIndex(@NonNull final String userId, @NonNull final Integer index,
                                 @NonNull final String name, @NonNull final String description) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (name.isEmpty()) throw new NameEmptyException();
        if (description.isEmpty()) throw new DescriptionEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            final int size = repository.getSize(userId);
            if (index == null || index < 0 || index >= size) throw new IndexIncorrectException();
            final Project project = repository.findOneByIndex(userId, index);
            if (project == null) throw new ProjectNotFoundException();
            if (!project.getUserId().equals(userId)) throw new PermissionException();
            project.setName(name);
            project.setDescription(description);

            repository.update(project);
            session.commit();
            return project;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project changeProjectStatusById(@NonNull final String userId, @NonNull final String id, @NonNull final Status status) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (id.isEmpty()) throw new ProjectIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            final Project project = repository.findOneById(userId, id);
            if (project == null) throw new ProjectNotFoundException();
            if (!project.getUserId().equals(userId)) throw new PermissionException();
            project.setStatus(status);

            repository.update(project);
            session.commit();
            return project;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project changeProjectStatusByIndex(@NonNull final String userId, @NonNull final Integer index, @NonNull final Status status) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            final int size = repository.getSize(userId);
            if (index == null || index < 0 || index >= size) throw new IndexIncorrectException();
            final Project project = repository.findOneByIndex(userId, index);
            if (project == null) throw new ProjectNotFoundException();
            if (!project.getUserId().equals(userId)) throw new PermissionException();
            project.setStatus(status);

            repository.update(project);
            session.commit();
            return project;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project findOneById(@NonNull final String userId, @NonNull final String id) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        if (id.isEmpty()) throw new ProjectIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            final Project project = repository.findOneById(userId, id);
            if (project == null) throw new ProjectNotFoundException();
            return project;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Project findOneByIndex(@NonNull final String userId, @NonNull final Integer index) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            final int size = repository.getSize(userId);
            if (index == null || index < 0 || index >= size) throw new IndexIncorrectException();
            final Project project = repository.findOneByIndex(userId, index);
            if (project == null) throw new ProjectNotFoundException();
            return project;
        }
    }

    @Override
    @SneakyThrows
    public int getSize(@NonNull final String userId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            return session.getMapper(IProjectRepository.class).getSize(userId);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Project> findAll(@NonNull final String userId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            return session.getMapper(IProjectRepository.class).findAll(userId);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Project> findAll(@NonNull final String userId, final Sort sort) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            final IProjectRepository repository = session.getMapper(IProjectRepository.class);
            if (sort == null) return repository.findAll(userId);
            if (sort == Sort.BY_NAME) return repository.findAllOrderByNameAsc(userId);
            if (sort == Sort.BY_CREATED) return repository.findAllOrderByCreatedAsc(userId);
            return repository.findAllOrderByIdAsc(userId);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Project> findAllOrderById(@NonNull final String userId) {
        return findAll(userId, null);
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Project> findAllOrderByName(@NonNull final String userId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (final SqlSession session = connectionService.getSqlSession()) {
            return session.getMapper(IProjectRepository.class).findAllOrderByNameAsc(userId);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<Project> findAllOrderByCreated(@NonNull final String userId) {
        if (userId.isEmpty()) throw new UserIdEmptyException();
        try (final SqlSession session = connectionService.getSqlSession()) {
            return session.getMapper(IProjectRepository.class).findAllOrderByCreatedAsc(userId);
        }
    }

}