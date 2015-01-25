package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean {
  private static int timesInstantiated = 0;

  private PrototypeBean prototypeBean;

  @Autowired
  public SingletonBean(PrototypeBean prototypeBean) {
    this.prototypeBean = prototypeBean;
    timesInstantiated++;
  }

  public String getPrototypeBeanId() {
    return String.valueOf(prototypeBean.getId());
  }

  public static int getTimesInstantiated() {
    return timesInstantiated;
  }

}
