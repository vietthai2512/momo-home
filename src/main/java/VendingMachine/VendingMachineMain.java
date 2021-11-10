package VendingMachine;

import VendingMachine.Core.Money;
import VendingMachine.Core.Product;

import java.util.Scanner;

public class VendingMachineMain {
  public static void main(String[] args) {
    VendingMachine vm = new VendingMachine();

    while (true) {
      System.out.println("\n______Vending machine______\n");

      //System.out.println("Promotion: If there are 3 consecutive purchases of the same product, you will have a chance to receive a product for free.\n");

      System.out.println("Current balances: " + vm.getCurrentBalances() + " VND\n");

      System.out.println("Please select an option:");
      System.out.println("Option 1: Insert money");
      System.out.println("Option 2: Buy product");
      System.out.println("Option 3: Refund");
      System.out.println("Option 4: Quit");
      System.out.println("Option 5: Quit and return next day!");

      System.out.print("\nYour option: ");
      Scanner scanner = new Scanner(System.in);
      int selected = scanner.nextInt();
      System.out.println("________________________________");

      switch (selected) {
        case 1:
          System.out.println("\nMoney notes available: ");
          int index = 1;
          for (Money m: Money.values()) {
            System.out.println(index + ": " + m.toString() + ", ");
            index++;
          }
          System.out.println("Press 6 to return to main menu!");

          while (true) {
            System.out.println("\nCurrent balances: " + vm.getCurrentBalances() + " VND");
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
              System.out.println("\nProduct available:");
              System.out.println(vm.getProductInventory().toString());

              System.out.println("Current balances: " + vm.getCurrentBalances() + " VND");
              System.out.println("Product in cart: \n" + vm.getProductInCart().toString());

              System.out.print("Enter product name (Soda, Coke, Pepsi) or enter (Purchase, Cancel): ");
              Scanner scProd = new Scanner(System.in);
              String prodName = scProd.nextLine();

              try {
                switch (prodName) {
                  case "Coke":
                    vm.selectProduct(Product.COKE, 1);
                    continue;
                  case "Pepsi":
                    vm.selectProduct(Product.PEPSI, 1);
                    continue;
                  case "Soda":
                    vm.selectProduct(Product.SODA, 1);
                    continue;
                  case "Purchase":

                  case "Cancel":
                    vm.refund();
                    continue;
                  default:
                    throw new Exception("Invalid product name or command");
                }
              } catch (Exception e) {
                System.out.println(e.getMessage());
              }
            }
          }
          continue;

        case 3:
          if (vm.getCurrentBalances() == 0) {
            System.out.println("--> Please insert money first!!! <--");
          } else {
            vm.refund();
          }
          continue;

        case 4:
          if (vm.getCurrentBalances() > 0) {
            //while (true) {
            //
            //}
          }

          return;

        default:
          System.out.println("Please choose another option.");
      }
    }
  }
}
