package app;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, ApplicationWithMockTest.MockTestConfiguration.class } )
public class ApplicationWithMockTest {
  private static Logger logger = LoggerFactory.getLogger(ApplicationWithMockTest.class);

  private static boolean prototypeInvoked = false;

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void commandLineRunner_usesPrototypeBean() {
    PrototypeBean bean = applicationContext.getBean(PrototypeBean.class);

    assertThat(bean.getId()).isEqualTo(0);
    assertThat(prototypeInvoked);
  }

  @Configuration
  public static class MockTestConfiguration {
    @Bean
    public static OverrideBeanWithMock overridePrototypeBean() {
      return new OverrideBeanWithMock("prototypeBean", MockTestConfiguration.scopedProxyForMock());
    }

    public static ScopedProxyFactoryBean scopedProxyForMock() {
      ScopedProxyFactoryBean factoryBean = new ScopedProxyFactoryBean();
      factoryBean.setTargetBeanName("proxyTarget.mockPrototypeBean");
      return factoryBean;
    }

    @Bean(name = "proxyTarget.mockPrototypeBean")
    @Scope(value = "prototype")
    public static PrototypeBean mockPrototypeBean() {
      logger.info("MOCK CREATED");
      PrototypeBean mock = mock(PrototypeBean.class);
      when(mock.getId()).then(invocationOnMock -> {
        logger.info("MOCK INVOKED");
        prototypeInvoked = true;
        return invocationOnMock.callRealMethod();
      });
      return mock;
    }
  }
}