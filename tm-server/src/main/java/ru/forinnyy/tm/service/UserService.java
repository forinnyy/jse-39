package ru.forinnyy.tm.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import ru.forinnyy.tm.api.repository.IProjectRepository;
import ru.forinnyy.tm.api.repository.ITaskRepository;
import ru.forinnyy.tm.api.repository.IUserRepository;
import ru.forinnyy.tm.api.service.IConnectionService;
import ru.forinnyy.tm.api.service.IPropertyService;
import ru.forinnyy.tm.api.service.IUserService;
import ru.forinnyy.tm.enumerated.Role;
import ru.forinnyy.tm.enumerated.Sort;
import ru.forinnyy.tm.exception.entity.UserNotFoundException;
import ru.forinnyy.tm.exception.field.EmailEmptyException;
import ru.forinnyy.tm.exception.field.IdEmptyException;
import ru.forinnyy.tm.exception.field.LoginEmptyException;
import ru.forinnyy.tm.exception.field.PasswordEmptyException;
import ru.forinnyy.tm.exception.user.ExistsEmailException;
import ru.forinnyy.tm.exception.user.ExistsLoginException;
import ru.forinnyy.tm.model.User;
import ru.forinnyy.tm.util.HashUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class UserService implements IUserService {

    @NonNull
    private final IPropertyService propertyService;

    @NonNull
    private final IConnectionService connectionService;

    public UserService(@NonNull final IPropertyService propertyService,
                       @NonNull final IConnectionService connectionService) {
        this.propertyService = propertyService;
        this.connectionService = connectionService;
    }

    @Override
    @SneakyThrows
    public void initTable() {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            repository.initTable();
            session.commit();
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User create(@NonNull final String login, @NonNull final String password) {
        if (login.isEmpty()) throw new LoginEmptyException();
        if (password.isEmpty()) throw new PasswordEmptyException();
        if (isLoginExist(login)) throw new ExistsLoginException();

        @NonNull final User user = new User();
        user.setLogin(login);
        user.setPasswordHash(HashUtil.salt(propertyService, password));
        user.setRole(Role.USUAL);
        user.setLocked(false);

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            repository.add(user);
            session.commit();
            return user;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User create(@NonNull final String login, @NonNull final String password, @NonNull final String email) {
        if (login.isEmpty()) throw new LoginEmptyException();
        if (password.isEmpty()) throw new PasswordEmptyException();
        if (email.isEmpty()) throw new EmailEmptyException();
        if (isLoginExist(login)) throw new ExistsLoginException();
        if (isEmailExist(email)) throw new ExistsEmailException();

        @NonNull final User user = new User();
        user.setLogin(login);
        user.setPasswordHash(HashUtil.salt(propertyService, password));
        user.setEmail(email);
        user.setRole(Role.USUAL);
        user.setLocked(false);

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            repository.add(user);
            session.commit();
            return user;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User create(@NonNull final String login, @NonNull final String password, @NonNull final Role role) {
        if (login.isEmpty()) throw new LoginEmptyException();
        if (password.isEmpty()) throw new PasswordEmptyException();
        if (isLoginExist(login)) throw new ExistsLoginException();

        @NonNull final User user = new User();
        user.setLogin(login);
        user.setPasswordHash(HashUtil.salt(propertyService, password));
        user.setRole(role);
        user.setLocked(false);

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            repository.add(user);
            session.commit();
            return user;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User findByLogin(@NonNull final String login) {
        if (login.isEmpty()) throw new LoginEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            return repository.findByLogin(login);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User findByEmail(@NonNull final String email) {
        if (email.isEmpty()) throw new EmailEmptyException();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            return repository.findByEmail(email);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User removeByLogin(@NonNull final String login) {
        if (login.isEmpty()) throw new LoginEmptyException();
        final User user = findByLogin(login);
        return remove(user);
    }

    @NonNull
    @Override
    @SneakyThrows
    public User removeByEmail(@NonNull final String email) {
        if (email.isEmpty()) throw new EmailEmptyException();
        final User user = findByEmail(email);
        return remove(user);
    }

    @NonNull
    @SneakyThrows
    public User remove(@NonNull final User model) {
        @NonNull final String userId = model.getId();
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository userRepository = session.getMapper(IUserRepository.class);
            userRepository.removeById(userId);
            session.commit();
            return model;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Boolean isLoginExist(@NonNull final String login) {
        if (login.isEmpty()) return false;
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            return repository.isLoginExist(login);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public Boolean isEmailExist(@NonNull final String email) {
        if (email.isEmpty()) return false;
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            return repository.isEmailExist(email);
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User setPassword(@NonNull final String id, @NonNull final String password) {
        if (id.isEmpty()) throw new IdEmptyException();
        if (password.isEmpty()) throw new PasswordEmptyException();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            final User user = repository.findOneById(id);
            if (user == null) throw new UserNotFoundException();

            user.setPasswordHash(HashUtil.salt(propertyService, password));
            repository.update(user);

            session.commit();
            return user;
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public User updateUser(@NonNull final String id,
                           @NonNull final String firstName,
                           @NonNull final String lastName,
                           @NonNull final String middleName) {
        if (id.isEmpty()) throw new IdEmptyException();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            final User user = repository.findOneById(id);
            if (user == null) throw new UserNotFoundException();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setMiddleName(middleName);
            repository.update(user);

            session.commit();
            return user;
        }
    }

    @Override
    @SneakyThrows
    public void lockUserByLogin(@NonNull final String login) {
        if (login.isEmpty()) throw new LoginEmptyException();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            final User user = repository.findByLogin(login);
            if (user == null) throw new UserNotFoundException();

            user.setLocked(true);
            repository.update(user);

            session.commit();
        }
    }

    @Override
    @SneakyThrows
    public void unlockUserByLogin(@NonNull final String login) {
        if (login.isEmpty()) throw new LoginEmptyException();

        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            final User user = repository.findByLogin(login);
            if (user == null) throw new UserNotFoundException();

            user.setLocked(false);
            repository.update(user);

            session.commit();
        }
    }

    @NonNull
    @Override
    public List<String> listProfiles() {
        return findAll().stream().map(User::getLogin).collect(Collectors.toList());
    }

    @NonNull
    @SneakyThrows
    public List<User> findAll() {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            return repository.findAll();
        }
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<User> findAll(final Sort sort) {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final IUserRepository repository = session.getMapper(IUserRepository.class);
            @NonNull final List<User> users = repository.findAll();
            if (sort == null) return users;
            return users.stream()
                    .sorted((Comparator<? super User>) sort.getComparator())
                    .collect(Collectors.toList());
        }
    }

}
