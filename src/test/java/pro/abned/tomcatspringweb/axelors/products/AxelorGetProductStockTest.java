package pro.abned.tomcatspringweb.axelors.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorModelResponse;
import pro.abned.tomcatspringweb.axelors.AxelorTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AxelorGetProductStockTest {
    @Autowired
    private AxelorManager axelorManager;

    @Test
    void testGet() {
        AxelorProduct product = new AxelorProduct();
        product.setId(45L);
        product.setVersion(0);

        AxelorModelResponse<AxelorProductStock> stockResponse = axelorManager.getStock().getForProduct(product);

        assertThat(stockResponse).isNotNull();

        AxelorProductStock stock = stockResponse.getData().getFirst();
        assertThat(stock.getAvailableStock()).isEqualTo(AxelorTestUtils.PRODUCT_STOCK);
        assertThat(stock.getRealQty()).isEqualTo(AxelorTestUtils.PRODUCT_STOCK);
        assertThat(stock.getFutureQty()).isEqualTo(AxelorTestUtils.PRODUCT_STOCK);
    }
}