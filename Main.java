import java.util.ArrayList;
import java.util.List;


interface Payable {
    void pay(double amount);
}


interface Notifier {
    void send(String message);
}


class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}


class Order {
    private int orderId;
    private List<Product> products;

    public Order(int orderId) {
        this.orderId = orderId;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateTotal() {
        double total = 0;

        for (Product product : products) {
            total += product.getPrice();
        }

        return total;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Product> getProducts() {
        return products;
    }
}


abstract class Payment implements Payable {
    public abstract void pay(double amount);
}


class CardPayment extends Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Картамен төленді: " + amount + " тг");
    }
}


class CashPayment extends Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Қолма-қол төленді: " + amount + " тг");
    }
}

class EmailNotifier implements Notifier {

    @Override
    public void send(String message) {
        System.out.println("Хабарлама: " + message);
    }
}


class OrderService {

    private Payable payment;
    private Notifier notifier;

    
    public OrderService(Payable payment, Notifier notifier) {
        this.payment = payment;
        this.notifier = notifier;
    }

    public void processOrder(Order order) {

        System.out.println("\nТапсырыс №" + order.getOrderId());
        System.out.println("Тауарлар:");

        for (Product product : order.getProducts()) {
            System.out.println("- " + product.getName()
                    + " : " + product.getPrice() + " тг");
        }

        double total = order.calculateTotal();

        System.out.println("Жалпы сома: " + total + " тг");

        payment.pay(total);

        notifier.send("№" + order.getOrderId()
                + " тапсырысы сәтті рәсімделді.");
    }
}


public class Main {

    public static void main(String[] args) {

        System.out.println("=== ТАПСЫРЫСТАРДЫ БАСҚАРУ ЖҮЙЕСІ ===");

     
        Product product1 = new Product("Ноутбук", 250000);
        Product product2 = new Product("Тінтуір", 5000);
        Product product3 = new Product("Құлаққап", 12000);

   
        Order order = new Order(1001);

        order.addProduct(product1);
        order.addProduct(product2);
        order.addProduct(product3);

       
        Payable payment = new CardPayment();
        Notifier notifier = new EmailNotifier();

        OrderService service =
                new OrderService(payment, notifier);

        service.processOrder(order);

      
        System.out.println("\n=== ҚОЛМА-ҚОЛ ТӨЛЕМ ===");

        Payable cashPayment = new CashPayment();

        OrderService cashService =
                new OrderService(cashPayment, notifier);

        cashService.processOrder(order);

        System.out.println("\nБарлық тапсырыстар өңделді!");
    }
}
