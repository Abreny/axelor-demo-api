package pro.abned.tomcatspringweb.services.products;

import jakarta.transaction.Transactional;
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

    public Product update(Long productId, ProductForm productForm) {
        final var toUpdate = _validate(productId);

        AttributeCopyManager.copyNotNull(productForm, toUpdate);

        return productRepository.save(toUpdate);
    }

    @Transactional
    public Product createOrUpdate(Product product) {
        Product prod = null;
        if (product.getId() != null) {
            prod = productRepository.findById(product.getId()).orElse(null);
        }
        if (product.getErpProductId() != null) {
            prod = productRepository.findOneByErpProductId(product.getErpProductId()).orElse(null);
        }
        if (prod == null) {
            return productRepository.save(product);
        }

        AttributeCopyManager.copyNotNull(product, prod);

        return productRepository.save(prod);
    }

    private Product _validate(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("product.get.not_found#" + id));
    }
}
