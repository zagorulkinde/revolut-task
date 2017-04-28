package my.interviews.service;

import my.interviews.dao.AccountMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;

import java.io.IOException;
import java.io.InputStream;

public class AccountService {


  private static final String MY_BATIS_CONFIG = "mybatis-config.xml";
  private static SqlSessionManager sessionManager;

  private AccountService() {
  }

  public synchronized static AccountMapper getMapper() {
    return getSession().getMapper(AccountMapper.class);
  }

  private static SqlSession getSession() {
    if (sessionManager == null) {
      try (InputStream inputStream = Resources.getResourceAsStream(MY_BATIS_CONFIG)) {
        sessionManager = SqlSessionManager.newInstance(inputStream);
      } catch (IOException e) {
        throw new Error("my batis config not found!");
      }
    }
    return sessionManager;
  }
}
