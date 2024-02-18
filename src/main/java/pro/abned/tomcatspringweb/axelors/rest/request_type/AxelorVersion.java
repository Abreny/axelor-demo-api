package pro.abned.tomcatspringweb.axelors.rest.request_type;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import pro.abned.tomcatspringweb.axelors.AxelorManager;

public class AxelorVersion implements AxelorRequestable {
    private final int version;
    private final String path;
    private final AxelorManager axelorManager;

    public AxelorVersion(int version, String path, AxelorManager axelorManager) {
        this.version = version;
        this.path = path;
        this.axelorManager = axelorManager;
    }

    @Override
    public ResponseEntity<String> execute() {
        final VersionRequest request = new VersionRequest();
        request.setVersion(version);
        return axelorManager.getRestTemplate().postForEntity(String.format("%s/%s", axelorManager.getAxelorSetting().getHost(), path), request, String.class);
    }

    @Override
    public IllegalArgumentException getRequestFailed() {
        return new IllegalArgumentException(String.format("axelor.get.with_version.error#[%s,%d]", axelorManager.getAxelorSetting().getHost(), version));
    }

    @Override
    public IllegalArgumentException getResponseFailed() {
        return new IllegalArgumentException(String.format("axelor.get.with_version.parser.error#[%s,%d]", axelorManager.getAxelorSetting().getHost(), version));
    }

    @Getter
    @Setter
    private static class VersionRequest {
        private Integer version;
    }
}
