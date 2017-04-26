package my.interviews.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options.FlushCachePolicy;
import java.util.List;

@SuppressWarnings("unused")
public interface AccountMapper {

  @Results({@Result(property = "id", column = "id"),
      @Result(property = "firstName", column = "firstname"),
      @Result(property = "LastName", column = "Lastname"),
      @Result(property = "balance", column = "balance")})

  @Select({"select * from accounts"})
  @Options(useCache = true)
  List<Account> getAllAccount();

  @Select({"select * from accounts where id = #{id}"})
  @Options(useCache = true)
  Account getAccount(long id);

  @Delete({"delete from accounts where id = #{id}"})
  @Options(useCache = true)
  boolean removeAccount(long id);

  @Options(flushCache = FlushCachePolicy.TRUE)
  @Insert({"insert into accounts (firstname, lastname, balance) "
      + "values (#{firstName}, #{lastName}, #{balance})"})
  void insertAccount(Account account);

  @Options(flushCache = FlushCachePolicy.TRUE)
  @Update({"UPDATE accounts SET balance = #{balance} WHERE id = #{id}"})
  void updateBalance(Account account);
}
