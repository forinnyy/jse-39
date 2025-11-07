package ru.forinnyy.tm.api.service;

import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;

public interface IConnectionService {

    SqlSession getSqlSession();

}
