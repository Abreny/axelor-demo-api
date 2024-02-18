package pro.abned.tomcatspringweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.abned.tomcatspringweb.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
