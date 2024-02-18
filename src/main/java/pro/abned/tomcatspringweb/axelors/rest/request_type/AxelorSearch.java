package pro.abned.tomcatspringweb.axelors.rest.request_type;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import pro.abned.tomcatspringweb.axelors.AxelorManager;

import java.util.List;

public class AxelorSearch implements AxelorRequestable {
    private final int offset;
    private final int limit;

    private final List<String> fields;

    private final String path;

    private final AxelorManager axelorManager;

    public AxelorSearch(int offset, int limit, String path, AxelorManager axelorManager, String... fields) {
        this.offset = offset;
        this.limit = limit;
        this.path = path;
        this.axelorManager = axelorManager;
        this.fields = List.of(fields);
    }

    @Override
    public ResponseEntity<String> execute() {
        final GetRequest request = new GetRequest();
        request.setOffset(offset);
        request.setLimit(limit);
        request.setFields(fields);
        return axelorManager.getRestTemplate().postForEntity(String.format("%s/%s", axelorManager.getAxelorSetting().getHost(), path), request, String.class);
    }

    @Override
    public IllegalArgumentException getRequestFailed() {
        return new IllegalArgumentException(String.format("axelor.get.error#[%s,%d,%d]", axelorManager.getAxelorSetting().getHost(), offset, limit));
    }

    @Override
    public IllegalArgumentException getResponseFailed() {
        return new IllegalArgumentException(String.format("axelor.get.parser.error#[%s,%d,%d]", axelorManager.getAxelorSetting().getHost(), offset, limit));
    }

    @Getter
    @Setter
    private static class GetRequest {
        private Integer offset;
        private Integer limit;
        private List<String> fields = List.of("id");
    }
}
