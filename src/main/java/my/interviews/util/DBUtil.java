package my.interviews.util;

import org.apache.ibatis.session.SqlSession;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class DBUtil {
  private static final String DATABASE_URL = "jdbc:h2:mem:~/revolut;USER=sa;";
  private static SqlSession session = null;
  private static final String LIQUIBASE_CHANGE_LOG_MASTER = "changelog/changelog-master.xml";

  private DBUtil() {
  }

  public static void startH2(String args[]) throws SQLException {
    Server server = Server.createTcpServer(args);
    server.setShutdownHandler(() -> session.close());
    Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    server.start();
  }

  public static void initDB() throws SQLException, LiquibaseException {
    Connection connection = DriverManager.getConnection(DATABASE_URL);
    Database database = DatabaseFactory.getInstance()
        .findCorrectDatabaseImplementation(new JdbcConnection(connection));
    Liquibase liquibase =
        new Liquibase(LIQUIBASE_CHANGE_LOG_MASTER, new ClassLoaderResourceAccessor(), database);
    liquibase.update(new Contexts(), new LabelExpression());
  }


}
