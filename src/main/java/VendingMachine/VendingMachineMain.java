package VendingMachine;

import VendingMachine.Core.Money;
import VendingMachine.Core.Product;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class VendingMachineMain {
  public static void main(String[] args) {
    VendingMachine vm = new VendingMachine();
    Promotion pm = new Promotion();
    ArrayList<Product> last3Products = new ArrayList<>();

    while (true) {
      System.out.println("\n+----------Vending machine----------+\n");

      System.out.println(pm);
      System.out.println("Money inside machine:\n" + vm.getMoneyInventory().toString());

      System.out.println("Last 3 products: ");
      last3Products.forEach(product -> System.out.println(product.toString()));

      System.out.println("\nCurrent balances: " + vm.getCurrentBalances() + " VND\n");

      System.out.println("Please select an option:");
      System.out.println("Option 1: Insert money");
      System.out.println("Option 2: Buy product");
      System.out.println("Option 3: Refund");
      System.out.println("Option 4: Quit");
      System.out.println("Option 5: Quit and return next day!");

      System.out.print("\nYour option: ");
      Scanner scanner = new Scanner(System.in);
      int selected = scanner.nextInt();

      switch (selected) {
        case 1:
          System.out.println("+-------------------------------+");
          System.out.println("Money notes available: ");
          int index = 1;
          for (Money m: Money.values()) {
            System.out.println(index + ": " + m.toString() + ", ");
            index++;
          }
          System.out.println("Press 6 to return to main menu!");

          while (true) {
            System.out.println("---------------------------------");
            System.out.println("Current balances: " + vm.getCurrentBalances() + " VND");
            System.out.print("Please insert money: ");
            int moneyInserted = new Scanner(System.in).nextInt();
            if (moneyInserted == 6) break;

            switch (moneyInserted) {
              case 1 -> vm.insertMoney(Money.TenK);
              case 2 -> vm.insertMoney(Money.TwentyK);
              case 3 -> vm.insertMoney(Money.FiftyK);
              case 4 -> vm.insertMoney(Money.OneHundredK);
              case 5 -> vm.insertMoney(Money.TwoHundredK);
              default -> System.out.println("Invalid money note");
            }
          }
          continue;

        case 2:
          if (vm.getCurrentBalances() == 0) {
            System.out.println("--> Please insert money first!!! <--");
          } else {
            while (true) {
              System.out.println("---------------------------------");
              System.out.println("Product available:");
              System.out.println(vm.getProductInventory().toString());

              System.out.println("Current balances: " + vm.getCurrentBalances() + " VND");

              if (vm.getProductInCart() == null) {
                System.out.print("Enter product name (Soda, Coke, Pepsi) or enter (Cancel): ");
                String input = new Scanner(System.in).nextLine();

                if (Objects.equals(input, "Cancel")) {
                  vm.emptyCart();
                  break;
                }

                try {
                  switch (input) {
                    case "Coke" -> vm.addToCart(Product.COKE);
                    case "Pepsi" -> vm.addToCart(Product.PEPSI);
                    case "Soda" -> vm.addToCart(Product.SODA);
                    default -> throw new Exception("--> Invalid product name or command <--");
                  }
                } catch (Exception e) {
                  System.out.println(e.getMessage());
                }
              } else {
                System.out.println("Product in cart: \n" + vm.getProductInCart().toString());
                System.out.print("\nEnter (Purchase, Cancel): ");
                String input = new Scanner(System.in).nextLine();

                if (Objects.equals(input, "Cancel")) {
                  vm.emptyCart();
                  break;
                } else if (Objects.equals(input, "Purchase")) {
                  try {
                    Product purchasedProduct = vm.purchase();

                    if (last3Products.size() == 3) {
                      last3Products.remove(0);
                    }
                    last3Products.add(purchasedProduct);

                    Product freeProduct = pm.canGetAFreeProduct(last3Products, vm.getProductInventory());
                    if (freeProduct != null) {
                      vm.giveAwayPromotion(freeProduct);
                      last3Products.clear();
                      System.out.println("Congratulation, you have received a free product!");
                    }
                    break;
                  } catch (Exception e) {
                    System.out.println(e.getMessage());
                  }
                } else System.out.println("--> Invalid product name or command <--");
              }
            }
          }
          continue;

        case 3:
          if (vm.getCurrentBalances() == 0) {
            System.out.println("--> Please insert money first!!! <--");
          } else {
            try {
              vm.refund();
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
          }
          continue;

        case 4:
          return;

        case 5:
          vm.reset();
          pm.nextDay();
          continue;

        default:
          System.out.println("Please choose another option.");
      }
    }
  }
}
