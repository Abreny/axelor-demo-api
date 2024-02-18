package pro.abned.tomcatspringweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "order_lines")
@Getter
@Setter
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;

    private LocalDate created;
    private LocalDate updated;

    @ManyToOne
    private Product product;

    @JsonIgnore
    @ManyToOne
    private Order order;

    public Double price() {
        return product.getPrice();
    }

    public Double priceHT() {
        return 0.8 * this.price();
    }

    public Double amount() {
        return this.quantity * product.getPrice();
    }

    public Double amountHT() {
        return 0.8 * this.amount();
    }
}
