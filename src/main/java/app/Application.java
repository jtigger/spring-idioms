package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(excludeFilters = @Filter(type = FilterType.REGEX, pattern = ".*TestConfig.*"))
@EnableAutoConfiguration
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Autowired
            private SingletonBean singletonBean;
            @Autowired
            private SingletonBean singletonBeanAgain;
            @Autowired
            private ApplicationContext applicationContext;

            @Override
            public void run(String... strings) throws Exception {
                logger.info(applicationContext.getBeanDefinitionNames().toString());
                logger.info("SingletonBean.prototype.id = " + singletonBean.getPrototypeBeanId());
                logger.info("SingletonBeanAgain.prototype.id = " + singletonBeanAgain.getPrototypeBeanId());
                logger.info("singletonBean instantiated " + SingletonBean.getTimesInstantiated() + " times.");
            }
        };
    }
}
