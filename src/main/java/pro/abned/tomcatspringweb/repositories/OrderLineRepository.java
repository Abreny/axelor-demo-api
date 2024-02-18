package pro.abned.tomcatspringweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.abned.tomcatspringweb.entities.Order;
import pro.abned.tomcatspringweb.entities.OrderLine;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
