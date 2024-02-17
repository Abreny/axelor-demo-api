package pro.abned.tomcatspringweb.services.products;

import org.springframework.stereotype.Service;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.repositories.ProductRepository;

@Service
public class ProductQuery {
    private final ProductRepository productRepository;

    public ProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long id) {
        var productOpts = productRepository.findById(id);

        if (productOpts.isEmpty()) {
            throw new IllegalArgumentException("products.get_product.not_found#" + id);
        }

        return productOpts.get();
    }
}
