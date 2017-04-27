package my.interviews.service;

import java.math.BigDecimal;

import my.interviews.dao.Account;

public abstract class TransferService {

  public abstract void transferMoney(BigDecimal amount);

  void actualTransfer(Account from, Account to, BigDecimal transferAmount) {
    BigDecimal fromBalance = from.getBalance();

    if (fromBalance.compareTo(transferAmount) < 1) {
      throw new IllegalArgumentException("from account balance must be more the transfer balance");
    }

    from.setBalance(fromBalance.subtract(transferAmount));
    BigDecimal toBalance = to.getBalance();
    to.setBalance(toBalance.add(transferAmount));
  }

}
