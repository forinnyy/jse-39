// ru/forinnyy/tm/service/SessionService.java
package ru.forinnyy.tm.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import ru.forinnyy.tm.api.repository.ISessionRepository;
import ru.forinnyy.tm.api.service.IConnectionService;
import ru.forinnyy.tm.api.service.ISessionService;
import ru.forinnyy.tm.model.Session;

public final class SessionService implements ISessionService {

    @NonNull
    private final IConnectionService connectionService;

    public SessionService(@NonNull final IConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    @SneakyThrows
    public void initTable() {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final ISessionRepository repository = session.getMapper(ISessionRepository.class);
            repository.initTable();
            session.commit();
        }
    }

    @SneakyThrows
    public Session add(@NonNull final Session entity) {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final ISessionRepository repository = session.getMapper(ISessionRepository.class);
            repository.add(entity);
            session.commit();
            return entity;
        }
    }

    @SneakyThrows
    public boolean existsById(@NonNull final String id) {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final ISessionRepository repository = session.getMapper(ISessionRepository.class);
            final Boolean exists = repository.existsById(id);
            return exists != null && exists;
        }
    }

    @SneakyThrows
    public void remove(@NonNull final Session entity) {
        try (@NonNull final SqlSession session = connectionService.getSqlSession()) {
            @NonNull final ISessionRepository repository = session.getMapper(ISessionRepository.class);
            repository.removeById(entity.getId());
            session.commit();
        }
    }

}