package my.interviews.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import my.interviews.dao.Account;
import my.interviews.dao.AccountMapper;
import my.interviews.service.AccountService;
import my.interviews.service.TransferMoney;
import my.interviews.service.TransferService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("money-transfer")
@Api("money-transfer")
public class TransferMoneyController {
  private final AccountMapper accountMapper = AccountService.getMapper();
  private final TransferMoney transferMoney = new TransferMoney(accountMapper);

  @ApiOperation(value = "Get account", notes = "Returns account by id", response = Account.class)
  @Path("getClient/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Account getClient(@PathParam("id") long id) {
    Account account = accountMapper.getAccount(id);

    if (account == null) {
      throw new IllegalArgumentException("Account with id:" + id + "could't be found");
    }

    return account;
  }

  @ApiOperation(value = "Transfer money between client", notes = "ok or error", response = String.class)
  @Path("transfer")
  @POST
  @Produces(MediaType.APPLICATION_FORM_URLENCODED)
  public String transferMoney(
      @FormParam("idFrom") long idFrom,
      @FormParam("idTo") long idTo, @FormParam("amount") BigDecimal amount) {

    TransferService transferService = transferMoney.getTransfer(idFrom, idTo);
    transferService.transferMoney(amount);

    return "ok";
  }


}
