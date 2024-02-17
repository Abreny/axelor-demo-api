package pro.abned.tomcatspringweb.controllers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.abned.tomcatspringweb.dtos.forms.ProductForm;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

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

    @Test
    void testGetById() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/products/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Double price = documentContext.read("$.price");
        Double quantity = documentContext.read("$.quantity");
        Double weight = documentContext.read("$.weight");
        Double volume = documentContext.read("$.volume");
        String code = documentContext.read("$.code");
        String ean = documentContext.read("$.ean");
        String name = documentContext.read("$.name");
        String description = documentContext.read("$.description");

        assertThat(id).isNotNull();
        assertThat(price).isEqualTo(23.99);
        assertThat(quantity).isEqualTo(20.0);
        assertThat(weight).isEqualTo(0.3);
        assertThat(volume).isEqualTo(0);
        assertThat(code).isEqualTo("P0000");
        assertThat(ean).isEqualTo("123456789000");
        assertThat(name).isEqualTo("Product Mocked Test");
        assertThat(description).isEqualTo("Product Mocked Description Test");
    }

    @Test
    void testCreate() {
        ProductForm productForm = getProductForm("Product Name Controller Create Test");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/v1/products", productForm, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewProduct = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewProduct, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Double price = documentContext.read("$.price");
        Double quantity = documentContext.read("$.quantity");
        Double weight = documentContext.read("$.weight");
        Double volume = documentContext.read("$.volume");
        String code = documentContext.read("$.code");
        String ean = documentContext.read("$.ean");
        String name = documentContext.read("$.name");
        String description = documentContext.read("$.description");

        assertThat(id).isNotNull();
        assertThat(price).isEqualTo(productForm.getPrice());
        assertThat(quantity).isEqualTo(productForm.getQuantity());
        assertThat(weight).isEqualTo(productForm.getWeight());
        assertThat(volume).isEqualTo(productForm.getVolume());
        assertThat(code).isEqualTo(productForm.getCode());
        assertThat(ean).isEqualTo(productForm.getEan());
        assertThat(name).isEqualTo(productForm.getName());
        assertThat(description).isEqualTo(productForm.getDescription());
    }

    @Test
    void testGetAll() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/v1/products", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());

        Integer count = documentContext.read("$.length()");
        assertThat(count).isGreaterThanOrEqualTo(1);

        Number id = documentContext.read("$[0].id");
        Double price = documentContext.read("$[0].price");
        Double quantity = documentContext.read("$[0].quantity");
        Double weight = documentContext.read("$[0].weight");
        Double volume = documentContext.read("$[0].volume");
        String code = documentContext.read("$[0].code");
        String ean = documentContext.read("$[0].ean");
        String name = documentContext.read("$[0].name");
        String description = documentContext.read("$[0].description");

        assertThat(id).isNotNull();
        assertThat(price).isEqualTo(23.99);
        assertThat(quantity).isEqualTo(20.0);
        assertThat(weight).isEqualTo(0.3);
        assertThat(volume).isEqualTo(0);
        assertThat(code).isEqualTo("P0000");
        assertThat(ean).isEqualTo("123456789000");
        assertThat(name).isEqualTo("Product Mocked Test");
        assertThat(description).isEqualTo("Product Mocked Description Test");
    }
}