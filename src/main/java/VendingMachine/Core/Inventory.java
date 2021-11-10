package VendingMachine.Core;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
  private final Map<T, Integer> inventory = new HashMap<>();

  public Map<T, Integer> getInventory() {
    return inventory;
  }

  public int getQuantity(T item) {
    Integer count = inventory.get(item);
    return count == null ? 0 : count;
  }

  public void addItem(T item, int quantity) {
    inventory.merge(item, quantity, Integer::sum);
  }

  public void removeItem(T item, int quantity) {
    Integer count = inventory.get(item);
    if (count != null) {
      inventory.put(item, count - quantity);
    }
  }

  public boolean isEmpty() {
    if (inventory.size() == 0)
      return true;

    int totalItem = 0;
    for (var temp: inventory.values())
      totalItem += temp;

    return totalItem == 0;
  }

  @Override
  public String toString() {
    StringBuilder temp = new StringBuilder();
    inventory.forEach((item, quantity) -> temp.append(item.toString()).append(", quantity: ").append(quantity).append("\n"));

    return temp.toString();
  }
}
