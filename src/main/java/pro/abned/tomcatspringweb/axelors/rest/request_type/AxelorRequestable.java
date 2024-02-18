package pro.abned.tomcatspringweb.axelors.rest.request_type;

import org.springframework.http.ResponseEntity;

public interface AxelorRequestable {
    ResponseEntity<String> execute();

    default IllegalArgumentException getRequestFailed() {
        return new IllegalArgumentException("axelor.request.error");
    }

    default IllegalArgumentException getResponseFailed() {
        return new IllegalArgumentException("axelor.response.parser.error");
    }
}
