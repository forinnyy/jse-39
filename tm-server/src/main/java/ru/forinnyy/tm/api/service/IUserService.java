package ru.forinnyy.tm.api.service;

import lombok.NonNull;
import ru.forinnyy.tm.enumerated.Role;
import ru.forinnyy.tm.enumerated.Sort;
import ru.forinnyy.tm.model.User;

import java.util.List;

public interface IUserService {

    void initTable();

    @NonNull
    User create(@NonNull String login, @NonNull String password);

    @NonNull
    User create(@NonNull String login, @NonNull String password, @NonNull String email);

    @NonNull
    User create(@NonNull String login, @NonNull String password, @NonNull Role role);

    @NonNull
    User findByLogin(@NonNull String login);

    @NonNull
    User findByEmail(@NonNull String email);

    @NonNull
    User findOneById(@NonNull String id);

    @NonNull
    User removeByLogin(@NonNull String login);

    @NonNull
    User removeByEmail(@NonNull String email);

    @NonNull
    Boolean isLoginExist(@NonNull String login);

    @NonNull
    Boolean isEmailExist(@NonNull String email);

    @NonNull
    User setPassword(@NonNull String id, @NonNull String password);

    @NonNull
    User updateUser(@NonNull String id,
                    @NonNull String firstName,
                    @NonNull String lastName,
                    @NonNull String middleName);

    void lockUserByLogin(@NonNull String login);

    void unlockUserByLogin(@NonNull String login);

    @NonNull
    List<String> listProfiles();

    @NonNull
    List<User> findAll();

    @NonNull
    List<User> findAll(Sort sort);

}