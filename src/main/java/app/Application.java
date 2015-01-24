package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*TestConfiguration"))
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    public static class CommandLineRunner1 implements CommandLineRunner {
        private SingletonBean singletonBean;
        private SingletonBean singletonBeanAgain;

        @Autowired
        public CommandLineRunner1(SingletonBean singletonBean, SingletonBean singletonBeanAgain) {
            this.singletonBean = singletonBean;
            this.singletonBeanAgain = singletonBeanAgain;
        }
        @Override
        public void run(String... strings) throws Exception {
            System.out.println(singletonBean.getPrototypeBeanId());
            System.out.println(singletonBeanAgain.getPrototypeBeanId());
        }
    }
}
