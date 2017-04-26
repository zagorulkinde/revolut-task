package my.interviews.transfer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import my.interviews.dao.Account;
import my.interviews.service.TransferService;
import my.interviews.service.TransferServiceFactory;

public class TransferServiceFactoryTest {
  private Account fromAccount = new Account();
  private Account toAccount = new Account();
  private BigDecimal transferAmount = BigDecimal.valueOf(50);

  @BeforeMethod
  public void setUp() throws Exception {
    fromAccount.setBalance(BigDecimal.valueOf(100));
    toAccount.setBalance(BigDecimal.ZERO);
  }

  @Test
  public void simpleTransferTest() {
    BigDecimal fromBalance = fromAccount.getBalance();
    BigDecimal toBalance = toAccount.getBalance();

    TransferService transferService = TransferServiceFactory.getTransfer(fromAccount, toAccount);
    transferService.transferMoney(transferAmount);

    Assert.assertEquals(fromAccount.getBalance(), fromBalance.subtract(transferAmount));
    Assert.assertEquals(toAccount.getBalance(), toBalance.add(transferAmount));
  }

}