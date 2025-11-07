package ru.forinnyy.tm.service;


import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import ru.forinnyy.tm.api.repository.IProjectRepository;
import ru.forinnyy.tm.api.repository.ISessionRepository;
import ru.forinnyy.tm.api.repository.ITaskRepository;
import ru.forinnyy.tm.api.repository.IUserRepository;
import ru.forinnyy.tm.api.service.IConnectionService;
import ru.forinnyy.tm.api.service.IPropertyService;

import javax.sql.DataSource;
import javax.transaction.Transaction;
import java.sql.Connection;

public final class ConnectionService implements IConnectionService {

    @NonNull
    private final IPropertyService propertyService;

    private final SqlSessionFactory sqlSessionFactory;

    public ConnectionService(@NonNull final IPropertyService propertyService) {
        this.propertyService = propertyService;
        this.sqlSessionFactory = getSqlSessionFactory();
    }

    @NonNull
    @Override
    @SneakyThrows
    public SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    @NonNull
    @SneakyThrows
    private SqlSessionFactory getSqlSessionFactory() {
        @NonNull final String username = propertyService.getDBUser();
        @NonNull final String password = propertyService.getDBPassword();
        @NonNull final String url = propertyService.getDBUrl();
        @NonNull final String driver = propertyService.getDBDriver();
        @NonNull final DataSource dataSource = new PooledDataSource(driver, url, username, password);
        @NonNull final TransactionFactory transactionFactory = new JdbcTransactionFactory();
        @NonNull final Environment environment = new Environment("tm", transactionFactory, dataSource);
        @NonNull final Configuration configuration = new Configuration(environment);
        configuration.addMapper(IProjectRepository.class);
        configuration.addMapper(ITaskRepository.class);
        configuration.addMapper(IUserRepository.class);
        configuration.addMapper(ISessionRepository.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

}

