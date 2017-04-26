package my.interviews.service;

import java.math.BigDecimal;

import my.interviews.dao.Account;

public class TransferServiceFactory {

  public static TransferService getTransfer(Account from, Account to) {
    return new TransferMoneyService(from, to);
  }

  private static class TransferMoneyService extends TransferService {

    private final Account from;
    private final Account to;

    private TransferMoneyService(Account from, Account to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public boolean transferMoney(final BigDecimal amount) {
      if (from.compareTo(to) >= 0) {
        synchronized (from) {
          synchronized (to) {
            actualTransfer(from, to, amount);
          }
        }

      } else {
        synchronized (to) {
          synchronized (from) {
            actualTransfer(from, to, amount);
          }
        }

      }

      return false;
    }

  }


}
