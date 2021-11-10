package VendingMachine.Core;

public enum Product {
  COKE("Coke", 10000),
  PEPSI("Pepsi", 10000),
  SODA("Soda", 20000);

  private String name;
  private int price;

  Product(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "- name: '" + name + '\'' +
            ", price: " + price +
            " VND";
  }
}
