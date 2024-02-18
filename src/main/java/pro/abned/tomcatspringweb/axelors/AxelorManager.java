package pro.abned.tomcatspringweb.axelors;

import lombok.Getter;
import org.springframework.web.client.RestTemplate;
import pro.abned.tomcatspringweb.axelors.auths.AxelorLogin;
import pro.abned.tomcatspringweb.axelors.orders.AxelorCreateOrder;
import pro.abned.tomcatspringweb.axelors.orders.AxelorCreateOrderLine;
import pro.abned.tomcatspringweb.axelors.products.AxelorGetProduct;
import pro.abned.tomcatspringweb.axelors.products.AxelorGetProductStock;
import pro.abned.tomcatspringweb.axelors.rest.SessionManager;

public class AxelorManager {
    @Getter
    private final RestTemplate restTemplate;

    @Getter
    private final AxelorSetting axelorSetting;

    private AxelorLogin login;
    private AxelorGetProduct getProduct;
    private AxelorGetProductStock getProductStock;

    private AxelorCreateOrder createOrder;
    private AxelorCreateOrderLine createOrderLine;

    public AxelorManager(RestTemplate restTemplate, AxelorSetting axelorSetting) {
        this.restTemplate = restTemplate;
        this.axelorSetting = axelorSetting;
    }

    public AxelorManager(AxelorSetting axelorSetting) {
        this.restTemplate = new SessionAwareRestTemplate(new SessionManager());
        this.axelorSetting = axelorSetting;
    }

    public AxelorLogin login() {
        if (login == null) {
            login = new AxelorLogin(this.restTemplate, axelorSetting);
        }
        return login;
    }

    public boolean checkLogin() {
        return login().authenticate(axelorSetting.getUsername(), axelorSetting.getPassword());
    }

    public AxelorGetProduct getProduct() {
        if (getProduct == null) {
            getProduct = new AxelorGetProduct(this);
        }
        return getProduct;
    }

    public AxelorGetProductStock getStock() {
        if (getProductStock == null) {
            getProductStock = new AxelorGetProductStock(this);
        }
        return getProductStock;
    }

    public AxelorCreateOrder createOrder() {
        if (createOrder == null) {
            createOrder = new AxelorCreateOrder(this);
        }
        return createOrder;
    }

    public AxelorCreateOrderLine createOrderLine() {
        if (createOrderLine == null) {
            createOrderLine = new AxelorCreateOrderLine(this);
        }
        return createOrderLine;
    }
}
