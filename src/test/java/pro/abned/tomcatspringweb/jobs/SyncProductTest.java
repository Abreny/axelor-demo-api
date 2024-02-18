package pro.abned.tomcatspringweb.jobs;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorModelResponse;
import pro.abned.tomcatspringweb.axelors.products.AxelorGetProduct;
import pro.abned.tomcatspringweb.axelors.products.AxelorGetProductStock;
import pro.abned.tomcatspringweb.axelors.products.AxelorProduct;
import pro.abned.tomcatspringweb.axelors.products.AxelorProductStock;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.repositories.ProductRepository;
import pro.abned.tomcatspringweb.services.products.ProductAction;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;

class SyncProductTest {
    @Test
    void testStart() {
        AxelorProduct product = new AxelorProduct();
        product.setId(1L);
        product.setCode("P000");
        product.setFullName("Product Fullname");
        product.setSalePrice(12.0);

        AxelorProductStock productStock = new AxelorProductStock();
        productStock.setAvailableStock(20.0);

        ProductRepository productRepository = mock(ProductRepository.class);
        ProductAction productAction = new ProductAction(productRepository);

        AxelorGetProduct productRequest = mock(AxelorGetProduct.class);
        AxelorModelResponse<AxelorProduct> products = new AxelorModelResponse<>();
        products.setData(List.of(product));
        when(productRequest.get(anyInt(), anyInt())).thenReturn(products);

        AxelorGetProductStock stockRequest = mock(AxelorGetProductStock.class);
        AxelorModelResponse<AxelorProductStock> stocks = new AxelorModelResponse<>();
        stocks.setData(List.of(productStock));
        when(stockRequest.getForProduct(any())).thenReturn(stocks);

        AxelorManager axelorManager = mock(AxelorManager.class);
        when(axelorManager.getProduct()).thenReturn(productRequest);
        when(axelorManager.getStock()).thenReturn(stockRequest);

        SyncProduct syncProduct = new SyncProduct(axelorManager, productAction);
        syncProduct.start();

        ArgumentCaptor<Product> createArgs = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(createArgs.capture());
        verify(axelorManager, times(1)).getProduct();
        verify(axelorManager, times(1)).getStock();

        verify(productRequest, times(1)).get(anyInt(), anyInt());
        verify(stockRequest, times(1)).getForProduct(any());

        Product args = createArgs.getValue();
        assertThat(args.getId()).isNull();
        assertThat(args.getErpProductId()).isEqualTo(product.getId());
        assertThat(args.getPrice()).isEqualTo(12.0);
        assertThat(args.getName()).isEqualTo(product.getFullName());
        assertThat(args.getDescription()).isEqualTo(product.getDescription());
    }
}