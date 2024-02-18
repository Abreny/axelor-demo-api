package pro.abned.tomcatspringweb.axelors.orders;

import lombok.Getter;
import lombok.Setter;
import pro.abned.tomcatspringweb.axelors.generics.AxelorAddress;
import pro.abned.tomcatspringweb.axelors.generics.AxelorCompany;
import pro.abned.tomcatspringweb.axelors.generics.AxelorCurrency;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class AxelorOrder {
    private Long id;
    private Integer saleOrderTypeSelect = 1; // standard
    private String creationDate;
    private String orderNumber;
    private String description;
    private String mainInvoicingAddressStr;
    private String saleOrderSeq;
    private Integer statusSelect = 3; // confirmed

    private Double totalCostPrice;
    private Double companyExTaxTotal;
    private Double taxTotal;
    private Double inTaxTotal;
    private Double exTaxTotal;
    private Double amountInvoiced;
    private Double accountedRevenue;
    private Double advanceTotal;

    private AxelorAddress deliveryAddress;
    private AxelorCompany company;
    private AxelorCustomer clientPartner;
    private AxelorCurrency currency;

    public AxelorOrder() {
        this.deliveryAddress = new AxelorAddress();
        this.deliveryAddress.setId(1L);

        this.company = new AxelorCompany();
        this.company.setId(1L);

        this.clientPartner = new AxelorCustomer();
        this.clientPartner.setId(40L);

        this.currency = new AxelorCurrency();
        this.currency.setId(46L);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.creationDate = simpleDateFormat.format(new Date());

        this.description = "MARKETPLACE DEMO ORDER";
        this.mainInvoicingAddressStr = "MARKETPLACE DEMO, 101 TEST";
    }
}
