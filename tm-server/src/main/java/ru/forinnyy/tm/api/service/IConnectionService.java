package ru.forinnyy.tm.api.service;

import org.apache.ibatis.session.SqlSession;

public interface IConnectionService {

    SqlSession getSqlSession();

}
