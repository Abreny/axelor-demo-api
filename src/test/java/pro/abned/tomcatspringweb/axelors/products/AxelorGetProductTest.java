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
class AxelorGetProductTest {
    @Autowired
    private AxelorManager axelorManager;

    @Test
    void testGet() {
        AxelorModelResponse<AxelorProduct> products = axelorManager.getProduct().get(0, 1);

        assertThat(products).isNotNull();
        assertThat(products.getOffset()).isEqualTo(0);
        assertThat(products.getTotal()).isGreaterThanOrEqualTo(1); // ensure that axelor has at least one product

        List<AxelorProduct> axelorProducts = products.getData();
        assertThat(axelorProducts).isNotEmpty();
        assertThat(axelorProducts.getFirst().getId()).isEqualTo(AxelorTestUtils.PRODUCT_ID);
        assertThat(axelorProducts.getFirst().getFullName()).isEqualTo(AxelorTestUtils.PRODUCT_NAME);
    }
}