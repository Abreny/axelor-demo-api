package pro.abned.tomcatspringweb.axelors.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SessionManager {
    private String cookie;

    public void addSessionInfoToRequest(HttpRequest request) {
        if (getCookie() != null) {
            request.getHeaders().add("Cookie", getCookie());
        }
    }

    public void extractSessionInfoFromResponse(ClientHttpResponse response) {
        List<String> setCookieHeaders = response.getHeaders().get("Set-Cookie");
        if (setCookieHeaders != null) {
            List<String> cookies = new ArrayList<>();
            setCookieHeaders.forEach(header -> cookies.add(HttpCookie.parse(header).getFirst().toString()));
            setCookie(String.join("; ", cookies.toArray(new String[0])));
        }
    }
}
