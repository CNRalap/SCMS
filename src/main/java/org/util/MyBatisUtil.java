package org.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/*
 *@author lyz
 *@version 2.0
 *@time 2023.3.5
 *@commit 一键生成mybatis的对话，不需要重复构建
 */
public class MyBatisUtil {
    private static SqlSessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static SqlSession getSession(boolean autoCommit) {
        return sessionFactory.openSession(autoCommit);
    }

}
