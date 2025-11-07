package ru.forinnyy.tm.api.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.forinnyy.tm.model.Session;

public interface ISessionService {

    @SneakyThrows
    void initTable();

    @SneakyThrows
    Session add(@NonNull Session entity);

    @SneakyThrows
    boolean existsById(@NonNull String id);

    @SneakyThrows
    void remove(@NonNull Session entity);

}
