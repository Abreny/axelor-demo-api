package pro.abned.tomcatspringweb.axelors.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AxelorProductStock {
    private Long id;
    private Double realQty;
    private Double futureQty;
    private Double allocatedQty;
    private Double saleOrderQty;
    private Double purchaseOrderQty;
    private Double availableStock;
    private Double buildingQty;
    private Double consumeManufOrderQty;
    private Double missingManufOrderQty;
}
