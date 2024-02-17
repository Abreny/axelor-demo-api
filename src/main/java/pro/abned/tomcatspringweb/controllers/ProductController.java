package pro.abned.tomcatspringweb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pro.abned.tomcatspringweb.dtos.forms.ProductForm;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.services.products.ProductAction;
import pro.abned.tomcatspringweb.services.products.ProductQuery;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin()
public class ProductController {
    private final ProductAction productAction;
    private final ProductQuery productQuery;

    public ProductController(ProductAction productAction, ProductQuery productQuery) {
        this.productAction = productAction;
        this.productQuery = productQuery;
    }

    @GetMapping("products")
    public List<Product> getAllProducts() {
        return productQuery.getAll();
    }

    @PostMapping("products")
    public ResponseEntity<Void> create(@RequestBody ProductForm productForm, UriComponentsBuilder ucb) {
        final Product created = productAction.create(productForm);
        final URI createdUri = ucb.path("api/v1/products/{id}").buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(createdUri).build();
    }

    @GetMapping("products/{id:\\d+}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productQuery.getProduct(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }
}
