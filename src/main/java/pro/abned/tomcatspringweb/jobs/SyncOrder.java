package pro.abned.tomcatspringweb.jobs;

import org.springframework.stereotype.Service;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorModelResponse;
import pro.abned.tomcatspringweb.axelors.orders.AxelorOrder;
import pro.abned.tomcatspringweb.axelors.orders.AxelorOrderLine;
import pro.abned.tomcatspringweb.axelors.products.AxelorProduct;
import pro.abned.tomcatspringweb.entities.Order;
import pro.abned.tomcatspringweb.entities.OrderLine;

@Service
public class SyncOrder {
    private final AxelorManager axelorManager;

    public SyncOrder(AxelorManager axelorManager) {
        this.axelorManager = axelorManager;
    }

    public void storeOrder(Order order) {
        AxelorOrder newOrder = new AxelorOrder();
        newOrder.setOrderNumber(order.getId().toString());
        newOrder.setSaleOrderSeq("M" + order.getId());

        final var htva = order.amountHT();
        final var ttc = order.amount();

        newOrder.setTotalCostPrice(ttc);
        newOrder.setCompanyExTaxTotal(htva);
        newOrder.setTaxTotal(ttc - htva);
        newOrder.setInTaxTotal(ttc);
        newOrder.setExTaxTotal(htva);
        newOrder.setAmountInvoiced(ttc);

        AxelorModelResponse<AxelorOrder> orderResponse = axelorManager.createOrder().create(newOrder);
        final var erpOrderId = orderResponse.getData().getFirst().getId();
        for (OrderLine item: order.getItems()) {
            final AxelorOrderLine newOrderLine = new AxelorOrderLine();
            newOrderLine.setProductName(item.getProduct().getName());
            newOrderLine.setDescription(item.getProduct().getDescription());
            newOrderLine.setFullName(newOrderLine.getProductName());
            newOrderLine.setQty(item.getQuantity());
            newOrderLine.setPrice(item.price());
            newOrderLine.setInTaxPrice(item.price());
            newOrderLine.setExTaxTotal(item.amountHT());
            newOrderLine.setInTaxTotal(item.amount());

            AxelorOrder parentOrder = new AxelorOrder();
            parentOrder.setId(erpOrderId);
            newOrderLine.setSaleOrder(parentOrder);

            if (item.getProduct().getErpProductId() != null) {
                AxelorProduct product = new AxelorProduct();
                product.setId(item.getProduct().getErpProductId());
                newOrderLine.setProduct(product);
            }

            axelorManager.createOrderLine().create(newOrderLine);
        }
    }
}
