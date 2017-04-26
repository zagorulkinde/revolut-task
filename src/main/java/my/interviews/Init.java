package my.interviews;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.URL;
import java.util.Optional;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import my.interviews.controller.TransferMoneyController;
import my.interviews.util.DBUtil;

public class Init {
  private static final int SERVER_PORT = 8080;
  private static final String API_DOCS = "/api-docs/";
  private static final String SWAGGER_RESOURCE = "swagger-ui";

  public static void main(String[] args) throws Exception {
    try {
      Resource.setDefaultUseCaches(false);
      buildSwagger();

      // Should be disabled in prod env
      DBUtil.startH2(args);
      DBUtil.initDB();

      Server server = new Server(SERVER_PORT);
      server.setHandler(initHandlers());
      server.start();
      server.join();
    } catch (Exception e) {
      throw new Error(e);
    }
  }

  private static HandlerList initHandlers() throws Exception {
    final HandlerList handlers = new HandlerList();

    handlers.addHandler(buildSwaggerUI());
    handlers.addHandler(buildContext());
    return handlers;
  }

  private static void buildSwagger() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0.5");
    beanConfig.setResourcePackage(TransferMoneyController.class.getPackage().getName());
    beanConfig.setScan(true);
    beanConfig.setBasePath("/");
  }

  private static ContextHandler buildContext() {
    ResourceConfig resourceConfig = new ResourceConfig();

    resourceConfig.packages(TransferMoneyController.class.getPackage().getName(),
        ApiListingResource.class.getPackage().getName());
    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder entityBrowser = new ServletHolder(servletContainer);
    ServletContextHandler entityBrowserContext =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    entityBrowserContext.setContextPath("/");
    entityBrowserContext.addServlet(entityBrowser, "/*");

    return entityBrowserContext;
  }

  private static ContextHandler buildSwaggerUI() throws Exception {
    final ResourceHandler swaggerUIResourceHandler = new ResourceHandler();
    Optional<URL> resourceBase = Optional.ofNullable(Init.class.getClassLoader().getResource(SWAGGER_RESOURCE));
    swaggerUIResourceHandler
        .setResourceBase(resourceBase.orElseThrow(() -> new RuntimeException("Could't find swagger-ui resource"))
            .toURI()
            .toString());

    final ContextHandler swaggerUIContext = new ContextHandler();
    swaggerUIContext.setContextPath(API_DOCS);
    swaggerUIContext.setHandler(swaggerUIResourceHandler);

    return swaggerUIContext;
  }

}
