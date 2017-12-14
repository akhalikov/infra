package akhalikov;

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import java.util.LinkedHashSet;
import java.util.Set;

public class JerseyResourceConfigBuilder {

  private final Set<Class<?>> resources = new LinkedHashSet<>();

  public void addResource(final Object resourceBean) {
    resources.add(resourceBean.getClass());
  }

  public ResourceConfig createResourceConfig() {
    return new DefaultResourceConfig(resources);
  }
}
