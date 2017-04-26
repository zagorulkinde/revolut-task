package my.interviews.controller;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import my.interviews.dao.Account;
import my.interviews.dao.AccountMapper;
import my.interviews.service.AccountService;
import my.interviews.service.TransferService;
import my.interviews.service.TransferServiceFactory;

@Path("money-transfer")
@Api("money-transfer")
public class TransferMoneyController {
  private final AccountMapper accountMapper = AccountService.getMapper();

  @ApiOperation(value = "Get account", notes = "Returns account by id", response = Account.class)
  @Path("getClient/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Account getClient(@PathParam("id") long id) {
    return accountMapper.getAccount(id);
  }

  @ApiOperation(value = "Transfer money between client", notes = "Returns param with balance", response = Account.class)
  @Path("transfer")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Account transferMoney(
      @FormParam("idFrom") long idFrom,
      @FormParam("idTo") long idTo, @FormParam("amount") BigDecimal amount) {

    Account fromAccount = accountMapper.getAccount(idFrom);
    Account toAccount = accountMapper.getAccount(idTo);

    TransferService transferService = TransferServiceFactory.getTransfer(fromAccount, toAccount);
    transferService.transferMoney(amount);

    accountMapper.updateBalance(fromAccount);
    accountMapper.updateBalance(toAccount);

    return toAccount;
  }


}
