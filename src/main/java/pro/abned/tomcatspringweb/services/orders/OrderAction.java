package pro.abned.tomcatspringweb.services.orders;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pro.abned.tomcatspringweb.dtos.forms.CheckoutForm;
import pro.abned.tomcatspringweb.dtos.forms.CheckoutItemForm;
import pro.abned.tomcatspringweb.entities.Order;
import pro.abned.tomcatspringweb.entities.OrderLine;
import pro.abned.tomcatspringweb.entities.Product;
import pro.abned.tomcatspringweb.repositories.OrderLineRepository;
import pro.abned.tomcatspringweb.repositories.OrderRepository;
import pro.abned.tomcatspringweb.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderAction {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;

    public OrderAction(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order create(CheckoutForm form) {
        if (form.getProducts() == null || form.getProducts().isEmpty()) {
            throw new IllegalArgumentException("order.create.request_invalid");
        }
        final Order toCreate = new Order();
        toCreate.setCreated(LocalDate.now());
        toCreate.setUpdated(LocalDate.now());

        orderRepository.save(toCreate);

        List<OrderLine> orderLines = new ArrayList<>();
        for (CheckoutItemForm itemForm: form.getProducts()) {
            final OrderLine orderLine = new OrderLine();
            orderLine.setCreated(LocalDate.now());
            orderLine.setUpdated(LocalDate.now());
            orderLine.setProduct(_validateProduct(itemForm.getProduct()));
            orderLine.setQuantity(itemForm.getQuantity());

            toCreate.addOrderLine(orderLine);

            orderLines.add(orderLine);
        }

        orderLineRepository.saveAll(orderLines);

        return toCreate;
    }

    private Product _validateProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("order.create.product_invalid#" + id));
    }
}
