package VendingMachine;

import VendingMachine.Core.Inventory;
import VendingMachine.Core.Product;

import java.util.ArrayList;
import java.util.Random;

public class Promotion {
  private int day = 1;
  private double chance = 0.1;
  private final int LIMIT = 50000;
  private int budget = LIMIT;

  public Product canGetAFreeProduct(ArrayList<Product> last3Products, Inventory<Product> productInventory) {
    if (last3Products.size() == 3
            && last3Products.get(0) == last3Products.get(1)
            && last3Products.get(1) == last3Products.get(2)) {
      Product p = last3Products.get(0);
      if (budget >= p.getPrice() && productInventory.getQuantity(p) > 0) {
        Random rand = new Random();
        if (rand.nextDouble() <= chance) {
          budget -= p.getPrice();
          return p;
        }
      }
    }
    return null;
  }

  public void nextDay() {
    if (budget > 0) {
      chance *= 1.5;
      if (chance > 1) chance = 1;
    } else {
      chance = 0.1;
    }
    budget = LIMIT;
    day++;
  }

  @Override
  public String toString() {
    return String.format("""
            Day: %d.
            Promotion:
            - If there are 3 consecutive purchases of the same product, you will have a %.2f%% chance to receive a product for free.
            - Today promotion budget: %d VND
            """, day, chance * 100, budget);
  }
}
