package hospital;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Catalysts on 8/9/2015.
 */

@EnableCaching
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"hospital.repository"})
@PropertySource(value = "classpath:application.properties")
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
