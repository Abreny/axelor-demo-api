package pro.abned.tomcatspringweb.services.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.repositories.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductQueryTest {
    private ProductRepository productRepository;
    private ProductQuery productQuery;

    @BeforeEach
    protected void setUp() {
        productRepository = mock(ProductRepository.class);
        productQuery = new ProductQuery(productRepository);
    }

    @Test
    void testCreate() {
        Product result = new Product();
        result.setId(1L);

        when(productRepository.findById(any())).thenReturn(Optional.of(result));

        Product product = productQuery.getProduct(1L);

        ArgumentCaptor<Long> getCapture = ArgumentCaptor.forClass(Long.class);
        verify(productRepository, times(1)).findById(getCapture.capture());

        assertThat(product).isEqualTo(result);

        assertThat(getCapture.getValue()).isEqualTo(1);
    }
}