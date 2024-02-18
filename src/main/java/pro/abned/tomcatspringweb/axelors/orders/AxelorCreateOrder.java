package pro.abned.tomcatspringweb.axelors.orders;

import com.fasterxml.jackson.core.type.TypeReference;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorModelResponse;
import pro.abned.tomcatspringweb.axelors.AxelorRequest;

public class AxelorCreateOrder extends AxelorRequest<AxelorOrder> {
    public static final String PATH = "ws/rest/com.axelor.apps.sale.db.SaleOrder";

    public AxelorCreateOrder(AxelorManager axelorManager) {
        super(axelorManager);
    }

    @Override
    protected TypeReference<AxelorModelResponse<AxelorOrder>> getResponseType() {
        return new TypeReference<AxelorModelResponse<AxelorOrder>>() {
        };
    }

    @Override
    protected String _getPath() {
        return PATH;
    }
}
