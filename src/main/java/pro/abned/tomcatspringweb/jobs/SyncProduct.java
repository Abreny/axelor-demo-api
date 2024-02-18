package pro.abned.tomcatspringweb.jobs;

import org.springframework.stereotype.Service;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.products.AxelorProduct;
import pro.abned.tomcatspringweb.axelors.products.AxelorProductStock;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.services.products.ProductAction;

import java.util.List;

@Service
public class SyncProduct {
    private final AxelorManager axelorManager;
    private final ProductAction productAction;

    public SyncProduct(AxelorManager axelorManager, ProductAction productAction) {
        this.axelorManager = axelorManager;
        this.productAction = productAction;
    }

    public void start() {
        final List<AxelorProduct> products = this.axelorManager.getProduct().get(0, Integer.MAX_VALUE).getData();
        if (products != null) {
            for (AxelorProduct product: products) {
                final List<AxelorProductStock> stock = this.axelorManager.getStock().getForProduct(product).getData();
                final Product marketPlaceProduct = _createMarketplaceProduct(product, stock);
                productAction.createOrUpdate(marketPlaceProduct);
            }
        }
    }

    private static Product _createMarketplaceProduct(AxelorProduct product, List<AxelorProductStock> stock) {
        final Product marketPlaceProduct = new Product();
        marketPlaceProduct.setPrice(product.getSalePrice());
        marketPlaceProduct.setCode(product.getCode());
        marketPlaceProduct.setName(product.getFullName());
        marketPlaceProduct.setDescription(product.getDescription());
        marketPlaceProduct.setErpProductId(product.getId());
        if (stock != null) {
            marketPlaceProduct.setQuantity(stock.getFirst().getAvailableStock());
        }
        return marketPlaceProduct;
    }
}
