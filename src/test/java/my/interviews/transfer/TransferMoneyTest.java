package my.interviews.transfer;

import my.interviews.dao.Account;
import my.interviews.dao.AccountMapper;
import my.interviews.service.TransferMoney;
import my.interviews.service.TransferService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferMoneyTest {
  private Account fromAccount = new Account();
  private Account toAccount = new Account();
  private BigDecimal transferAmount = BigDecimal.valueOf(50);
  private AccountMapper mapper = mock(AccountMapper.class);
  private TransferMoney transferMoney;

  @BeforeMethod
  public void setUp() throws Exception {
    fromAccount.setBalance(BigDecimal.valueOf(100));
    toAccount.setBalance(BigDecimal.ZERO);

    when(mapper.getAccount(1)).thenReturn(fromAccount);
    when(mapper.getAccount(2)).thenReturn(toAccount);

    transferMoney = new TransferMoney(mapper);
  }

  @Test
  public void simpleTransferTest() {
    BigDecimal fromBalance = fromAccount.getBalance();
    BigDecimal toBalance = toAccount.getBalance();

    TransferService transferService = transferMoney.getTransfer(1, 2);
    transferService.transferMoney(transferAmount);

    Assert.assertEquals(fromAccount.getBalance(), fromBalance.subtract(transferAmount));
    Assert.assertEquals(toAccount.getBalance(), toBalance.add(transferAmount));
  }

}