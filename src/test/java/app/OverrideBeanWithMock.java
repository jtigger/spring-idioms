package app;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

public class OverrideBeanWithMock implements BeanFactoryPostProcessor {
  private String beanName;
  private Object mock;

  public OverrideBeanWithMock(String beanName, Object mock) {
    this.beanName = beanName;
    this.mock = mock;
  }
  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory factory) throws BeansException {
    factory.registerSingleton(beanName, mock);
  }
}
