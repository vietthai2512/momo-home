package VendingMachine;

import VendingMachine.Core.Inventory;
import VendingMachine.Core.Money;
import VendingMachine.Core.Product;

import java.util.ArrayList;

public class VendingMachine {
  private final Inventory<Money> moneyInventory = new Inventory<>();
  private final Inventory<Product> productInventory = new Inventory<>();
  private Product productInCart;
  private long currentBalances = 0;

  public VendingMachine() {
    initialize();
  }

  private void initialize() {
    for (Money m: Money.values()) {
      moneyInventory.addItem(m, 1);
    }

    for (Product p: Product.values()) {
      productInventory.addItem(p, 10);
    }
  }

  public Inventory<Money> getMoneyInventory() {
    return moneyInventory;
  }

  public Inventory<Product> getProductInventory() {
    return productInventory;
  }

  public Product getProductInCart() {
    return productInCart;
  }

  public long getCurrentBalances() {
    return currentBalances;
  }

  public void insertMoney(Money money) {
    currentBalances += money.getAmount();
    moneyInventory.addItem(money, 1);
  }

  public void addToCart(Product p) throws Exception {
    if (productInventory.getQuantity(p) > 0) {
      if (currentBalances >= p.getPrice()) {
        currentBalances -= p.getPrice();
        productInCart = p;
        productInventory.removeItem(p, 1);
      } else {
        throw new Exception("Your balances is not enough, please select again");
      }
    } else {
      throw new Exception("Insufficient amount, please select again");
    }
  }

  public void emptyCart() {
    if (productInCart != null) {
      productInventory.addItem(productInCart, 1);
      currentBalances += productInCart.getPrice();
      productInCart = null;
    }
  }

  public Product purchase() throws Exception {
    if (productInCart == null) {
      throw new Exception("Your cart is empty");
    } else {
      Product temp = productInCart;
      ArrayList<Money> change = getChange(currentBalances);
      for (Money m: change) {
        moneyInventory.removeItem(m, 1);
      }
      currentBalances = 0;
      productInCart = null;
      return temp;
    }
  }

  public void giveAwayPromotion(Product p) {
    productInventory.removeItem(p, 1);
  }

  public void refund() throws Exception {
    ArrayList<Money> refund = getChange(currentBalances);
    for (Money m: refund) {
      moneyInventory.removeItem(m, 1);
    }

    currentBalances = 0;
    productInventory.addItem(productInCart, 1);
    productInCart = null;
  }

  public void reset() {
    productInCart = null;
    currentBalances = 0;
  }

  private ArrayList<Money> getChange(long amount) throws Exception {
    ArrayList<Money> change = new ArrayList<>();
    Inventory<Money> curMoneyInventory = new Inventory<>();
    long curBalances = amount;

    if (amount > 0) {
      while (curBalances > 0) {
        if (curBalances >= Money.TwoHundredK.getAmount()
                && moneyInventory.getQuantity(Money.TwoHundredK) > curMoneyInventory.getQuantity(Money.TwoHundredK)) {
          change.add(Money.TwoHundredK);
          curBalances -= Money.TwoHundredK.getAmount();
          curMoneyInventory.addItem(Money.TwoHundredK, 1);
        }

        else if (curBalances >= Money.OneHundredK.getAmount()
                && moneyInventory.getQuantity(Money.OneHundredK) > curMoneyInventory.getQuantity(Money.OneHundredK)) {
          change.add(Money.OneHundredK);
          curBalances -= Money.OneHundredK.getAmount();
          curMoneyInventory.addItem(Money.OneHundredK, 1);
        }

        else if (curBalances >= Money.FiftyK.getAmount()
                && moneyInventory.getQuantity(Money.FiftyK) > curMoneyInventory.getQuantity(Money.FiftyK)) {
          change.add(Money.FiftyK);
          curBalances -= Money.FiftyK.getAmount();
          curMoneyInventory.addItem(Money.FiftyK, 1);
        }

        else if (curBalances >= Money.TwentyK.getAmount()
                && moneyInventory.getQuantity(Money.TwentyK) > curMoneyInventory.getQuantity(Money.TwentyK)) {
          change.add(Money.TwentyK);
          curBalances -= Money.TwentyK.getAmount();
          curMoneyInventory.addItem(Money.TwentyK, 1);
        }

        else if (curBalances >= Money.TenK.getAmount()
                && moneyInventory.getQuantity(Money.TenK) > curMoneyInventory.getQuantity(Money.TenK)) {
          change.add(Money.TenK);
          curBalances -= Money.TenK.getAmount();
          curMoneyInventory.addItem(Money.TenK, 1);
        }

        else {
          throw new Exception("Insufficient change");
        }
      }
    }
    return change;
  }
}
