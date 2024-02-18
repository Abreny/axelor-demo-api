package pro.abned.tomcatspringweb.services.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pro.abned.tomcatspringweb.commons.AttributeCopyManager;
import pro.abned.tomcatspringweb.dtos.forms.ProductForm;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.repositories.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProductActionTest {
    private ProductRepository productRepository;
    private ProductAction productAction;

    private static ProductForm getProductForm(String name) {
        ProductForm productForm = new ProductForm();
        productForm.setCode("P0001");
        productForm.setName(name);
        productForm.setDescription("Product Description Test");
        productForm.setEan("1234567890123");
        productForm.setPrice(12.99);
        productForm.setQuantity(10.0);
        productForm.setVolume(0.0);
        productForm.setWeight(0.5);
        return productForm;
    }

    @BeforeEach
    protected void setUp() {
        productRepository = mock(ProductRepository.class);
        productAction = new ProductAction(productRepository);
    }

    @Test
    void testCreate() {
        ProductForm productForm = getProductForm("Product Name Product Action Create Test");

        Product result = new Product();
        result.setId(1L);

        when(productRepository.save(any())).thenReturn(result);

        Product created = productAction.create(productForm);

        ArgumentCaptor<Product> createCapture = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(createCapture.capture());

        assertThat(created).isEqualTo(result);

        Product createParams = createCapture.getValue();
        assertThat(createParams.getId()).isNull();
        assertEqualProduct(createParams, productForm);
    }

    private static void assertEqualProduct(Product createParams, ProductForm productForm) {
        assertThat(createParams.getCode()).isEqualTo(productForm.getCode());
        assertThat(createParams.getName()).isEqualTo(productForm.getName());
        assertThat(createParams.getEan()).isEqualTo(productForm.getEan());
        assertThat(createParams.getPrice()).isEqualTo(productForm.getPrice());
        assertThat(createParams.getVolume()).isEqualTo(productForm.getVolume());
        assertThat(createParams.getWeight()).isEqualTo(productForm.getWeight());
        assertThat(createParams.getDescription()).isEqualTo(productForm.getDescription());
        assertThat(createParams.getQuantity()).isEqualTo(productForm.getQuantity());
    }

    @Test
    void testCreateOrUpdate() {
        ProductForm productForm = getProductForm("Product Name Product Action Create Test");

        Product product = new Product();
        product.setId(1L);
        AttributeCopyManager.copyNotNull(productForm, product);

        when(productRepository.save(any())).thenReturn(product);

        Product created = productAction.createOrUpdate(product);

        ArgumentCaptor<Product> createCapture = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(createCapture.capture());
        verify(productRepository, times(1)).findById(eq(1L));

        assertThat(created).isEqualTo(product);

        Product createParams = createCapture.getValue();
        assertThat(createParams.getId()).isEqualTo(1L);
        assertEqualProduct(createParams, productForm);
    }
}
