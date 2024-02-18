package pro.abned.tomcatspringweb.axelors;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import pro.abned.tomcatspringweb.axelors.rest.SessionInterceptor;
import pro.abned.tomcatspringweb.axelors.rest.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SessionAwareRestTemplate extends RestTemplate {
    private final SessionManager sessionManager;

    public SessionAwareRestTemplate(SessionInterceptor sessionInterceptor) {
        this.sessionManager = sessionInterceptor.getSessionManager();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(sessionInterceptor);
        this.setInterceptors(interceptors);
    }

    public SessionAwareRestTemplate(SessionManager sessionManager) {
        this(new SessionInterceptor(sessionManager));
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}

