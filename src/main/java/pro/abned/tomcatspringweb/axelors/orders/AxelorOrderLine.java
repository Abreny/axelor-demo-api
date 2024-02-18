package pro.abned.tomcatspringweb.axelors.orders;

import lombok.Getter;
import lombok.Setter;
import pro.abned.tomcatspringweb.axelors.generics.AxelorUnit;
import pro.abned.tomcatspringweb.axelors.products.AxelorProduct;

@Getter
@Setter
public class AxelorOrderLine {
    private Long id;
    private String productName;
    private String description;
    private String fullName;
    private Boolean invoiced;
    private Double qty;

    private Double price;
    private Double inTaxPrice;
    private Double exTaxTotal;
    private Double inTaxTotal;

    private AxelorProduct product;
    private AxelorUnit unit;
    private AxelorOrder saleOrder;

    public AxelorOrderLine() {
        this.unit = new AxelorUnit();
        this.unit.setId(1);
    }
}
