package pro.abned.tomcatspringweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String name;

    @Column(length = 20)
    private String code;

    @Column(length = 20)
    private String ean;

    @Lob
    @Column(length = Integer.MAX_VALUE)
    private String description;

    private Double price;

    private Double quantity;

    private Double weight;

    private Double volume;
}
