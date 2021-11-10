package VendingMachine;

import VendingMachine.Core.Inventory;
import VendingMachine.Core.Money;
import VendingMachine.Core.Product;

import java.util.ArrayList;

public class VendingMachine {
  private final Inventory<Money> moneyInventory = new Inventory<>();
  private final Inventory<Product> productInventory = new Inventory<>();
  private final Inventory<Product> productInCart = new Inventory<>();
  private long currentBalances = 0;

  public Inventory<Product> getProductInventory() {
    return productInventory;
  }

  public Inventory<Product> getProductInCart() {
    return productInCart;
  }

  public long getCurrentBalances() {
    return currentBalances;
  }

  public VendingMachine() {
    initialize();
  }

  private void initialize() {
    for (Money m: Money.values()) {
      moneyInventory.addItem(m, 10);
    }

    for (Product p: Product.values()) {
      productInventory.addItem(p, 10);
    }
  }

  public void insertMoney(Money money) {
    currentBalances += money.getAmount();
    moneyInventory.addItem(money, 1);
  }

  public void selectProduct(Product p, int quantity) throws Exception {
    if (quantity > 0 && productInventory.getQuantity(p) > quantity) {
      if (currentBalances > (long) p.getPrice() * quantity) {
        currentBalances -= (long) p.getPrice() * quantity;
        productInCart.addItem(p, quantity);
        productInventory.removeItem(p, quantity);
      } else {
        throw new Exception("Your balances is not enough, please select again");
      }
    } else {
      throw new Exception("Insufficient amount, please select again");
    }
  }

  public void purchase() throws Exception {
    if (productInCart.isEmpty()) {
      throw new Exception("Your cart is empty");
    } else {

    }
  }

  public void refund() {
    ArrayList<Money> refund;
    try {
      refund = getChange(currentBalances);
      for (Money m: refund) {
        moneyInventory.removeItem(m, 1);
      }

      currentBalances = 0;
      productInCart.getInventory().forEach(productInventory::addItem);
      System.out.println("--> Refunded <--");
    } catch (Exception e) {
      System.out.println("--> Cannot refund <--");
    }
  }

  private ArrayList<Money> getChange(long amount) throws Exception {
    ArrayList<Money> change = new ArrayList<>();
    long curBalances = amount;

    if (amount > 0) {
      while (curBalances > 0) {
        if (curBalances >= Money.TwoHundredK.getAmount() && moneyInventory.getQuantity(Money.TwoHundredK) > 0) {
          change.add(Money.TwoHundredK);
          curBalances -= Money.TwoHundredK.getAmount();
        }

        else if (curBalances >= Money.OneHundredK.getAmount() && moneyInventory.getQuantity(Money.OneHundredK) > 0) {
          change.add(Money.OneHundredK);
          curBalances -= Money.OneHundredK.getAmount();
        }

        else if (curBalances >= Money.FiftyK.getAmount() && moneyInventory.getQuantity(Money.FiftyK) > 0) {
          change.add(Money.FiftyK);
          curBalances -= Money.FiftyK.getAmount();
        }

        else if (curBalances >= Money.TwentyK.getAmount() && moneyInventory.getQuantity(Money.TwentyK) > 0) {
          change.add(Money.TwentyK);
          curBalances -= Money.TwentyK.getAmount();
        }

        else if (curBalances >= Money.TenK.getAmount() && moneyInventory.getQuantity(Money.TenK) > 0) {
          change.add(Money.TenK);
          curBalances -= Money.TenK.getAmount();
        }

        else {
          throw new Exception("Insufficient change");
        }
      }
    }
    return change;
  }
}
