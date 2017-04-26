package my.interviews.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Objects;

@SuppressWarnings("unused")
public class Account implements Comparable<Account> {
  @JsonIgnore
  private transient long id;
  private String firstName;
  private String LastName;
  private BigDecimal balance;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return LastName;
  }

  public void setLastName(String lastName) {
    LastName = lastName;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @SuppressWarnings("NullableProblems")
  public int compareTo(Account o) {
    return Long.compare(id, o.id);
  }

  @Override
  public String toString() {
    return "Account{" + "id=" + id + ", firstName='" + firstName + '\'' + ", LastName='" + LastName
        + '\'' + ", balance=" + balance + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Account account = (Account) o;
    return getId() == account.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

}
