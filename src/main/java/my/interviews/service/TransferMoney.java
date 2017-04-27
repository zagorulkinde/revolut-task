package my.interviews.service;

import my.interviews.dao.Account;
import my.interviews.dao.AccountMapper;

import java.math.BigDecimal;

public class TransferMoney {
  private final AccountMapper accountMapper;

  public TransferMoney(AccountMapper accountMapper) {
    this.accountMapper = accountMapper;
  }

  public TransferService getTransfer(long from, long to) {
    return new TransferMoneyService(accountMapper, from, to);
  }

  private static class TransferMoneyService extends TransferService {
    private final Account from;
    private final Account to;
    private final AccountMapper mapper;

    private TransferMoneyService(AccountMapper mapper, long from, long to) {
      this.from = mapper.getAccount(from);
      this.to = mapper.getAccount(to);
      this.mapper = mapper;
    }

    @Override
    public void transferMoney(final BigDecimal amount) {
      if (from.compareTo(to) >= 0) {
        synchronized (from) {
          synchronized (to) {
            actualTransfer(from, to, amount);
            updateBalances();
          }
        }
      } else {
        synchronized (to) {
          synchronized (from) {
            actualTransfer(from, to, amount);
            updateBalances();
          }
        }
      }
    }

    private void updateBalances() {
      mapper.updateBalance(from);
      mapper.updateBalance(to);
    }
  }
}
