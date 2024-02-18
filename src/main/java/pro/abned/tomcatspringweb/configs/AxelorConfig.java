package pro.abned.tomcatspringweb.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorSetting;

@Configuration
public class AxelorConfig {
    @Value("${axelor.host}")
    private String host;

    @Value("${axelor.username}")
    private String username;

    @Value("${axelor.password}")
    private String password;

    @Bean
    public AxelorManager axelorManager() {
        final AxelorSetting setting = new AxelorSetting();
        setting.setHost(host);
        setting.setUsername(username);
        setting.setPassword(password);

        return new AxelorManager(setting);
    }
}
