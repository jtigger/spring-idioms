package app;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("prototypeBean")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypeBean {
  private static int count = 0;

  private int id;

  public PrototypeBean() {
    this.id = count++;
  }

  public int getId() {
    return id;
  }
}
