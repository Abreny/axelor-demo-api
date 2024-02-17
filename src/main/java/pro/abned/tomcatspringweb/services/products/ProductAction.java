package pro.abned.tomcatspringweb.services.products;

import org.springframework.stereotype.Service;
import pro.abned.tomcatspringweb.commons.AttributeCopyManager;
import pro.abned.tomcatspringweb.dtos.forms.ProductForm;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.repositories.ProductRepository;

@Service
public class ProductAction {
    private final ProductRepository productRepository;

    public ProductAction(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(ProductForm productForm) {
        final var toCreate = new Product();

        AttributeCopyManager.copyNotNull(productForm, toCreate);

        return productRepository.save(toCreate);
    }
}
