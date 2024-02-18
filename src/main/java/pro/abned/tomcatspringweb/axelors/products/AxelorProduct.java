package pro.abned.tomcatspringweb.axelors.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AxelorProduct {
    private Long id;
    private Double salePrice;
    private String fullName;
    private String description;
    private AxelorProductImage picture;
    private String code;
    private Integer version;
}
