package pro.abned.tomcatspringweb.controllers;

import org.springframework.web.bind.annotation.*;
import pro.abned.tomcatspringweb.dtos.forms.CheckoutForm;
import pro.abned.tomcatspringweb.entities.Order;
import pro.abned.tomcatspringweb.jobs.SyncOrder;
import pro.abned.tomcatspringweb.services.orders.OrderAction;

@RestController
@RequestMapping("api/v1")
@CrossOrigin()
public class OrderController {
    private final OrderAction orderAction;
    private final SyncOrder syncOrder;

    public OrderController(OrderAction orderAction, SyncOrder syncOrder) {
        this.orderAction = orderAction;
        this.syncOrder = syncOrder;
    }

    @PostMapping("checkout")
    public Order checkout(@RequestBody CheckoutForm form) {
        final Order created = orderAction.create(form);

        syncOrder.storeOrder(created);

        return created;
    }
}
