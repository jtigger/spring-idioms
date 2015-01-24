package app;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, ApplicationTest.MockTestConfiguration.class } )
public class ApplicationTest {

  private static boolean prototypeInvoked = false;

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void commandLineRunner_usesPrototypeBean() {
    PrototypeBean bean = applicationContext.getBean(PrototypeBean.class);

    assertThat(bean.getId()).isEqualTo(0);
  }

  @Configuration
  public static class MockTestConfiguration {
    @Bean
    public static OverrideBeanWithMock overridePrototypeBean() {
      return new OverrideBeanWithMock("prototypeBean", MockTestConfiguration.mockPrototypeBean());
    }

    public static PrototypeBean mockPrototypeBean() {
      System.out.println("MOCK CREATED");
      PrototypeBean mock = mock(PrototypeBean.class);
      when(mock.getId()).then(new Answer<Integer>() {
        @Override
        public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
          System.out.println("MOCK INVOKED");
          prototypeInvoked = true;
          return (Integer) invocationOnMock.callRealMethod();
        }
      });
      return mock;
    }
  }
}