package pro.abned.tomcatspringweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.abned.tomcatspringweb.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findOneByErpProductId(Long erpProductId);
    List<Product> findByOrderByIdAsc();
}
