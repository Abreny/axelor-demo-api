package pro.abned.tomcatspringweb.axelors.orders;

import com.fasterxml.jackson.core.type.TypeReference;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorModelResponse;
import pro.abned.tomcatspringweb.axelors.AxelorRequest;

public class AxelorCreateOrderLine extends AxelorRequest<AxelorOrderLine> {
    public static final String PATH = "ws/rest/com.axelor.apps.sale.db.SaleOrderLine";

    public AxelorCreateOrderLine(AxelorManager axelorManager) {
        super(axelorManager);
    }

    @Override
    protected TypeReference<AxelorModelResponse<AxelorOrderLine>> getResponseType() {
        return new TypeReference<AxelorModelResponse<AxelorOrderLine>>() {
        };
    }

    @Override
    protected String _getPath() {
        return PATH;
    }
}
