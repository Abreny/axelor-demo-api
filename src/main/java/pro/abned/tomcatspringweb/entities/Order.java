package pro.abned.tomcatspringweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate created;
    private LocalDate updated;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> items;

    public Order addOrderLine(OrderLine orderLine) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(orderLine);
        orderLine.setOrder(this);

        return this;
    }

    public Double amount() {
        if (items == null) {
            return 0.0;
        }
        return items.stream().mapToDouble(OrderLine::amount).sum();
    }

    public Double amountHT() {
        if (items == null) {
            return 0.0;
        }
        return items.stream().mapToDouble(OrderLine::amountHT).sum();
    }
}
