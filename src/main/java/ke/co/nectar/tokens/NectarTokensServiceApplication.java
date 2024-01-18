package ke.co.nectar.tokens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ke.co.nectar.tokens")
@ConfigurationPropertiesScan("ke.co.nectar.tokens.configurations")
@EnableJpaRepositories(basePackages="ke.co.nectar.tokens.repository")
public class NectarTokensServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NectarTokensServiceApplication.class, args);
    }
}
