package VendingMachine.Core;

public enum Money {
  TenK (10000),
  TwentyK (20000),
  FiftyK (50000),
  OneHundredK (100000),
  TwoHundredK (200000);

  private int amount;

  Money(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "" + amount + " VND";
  }
}
