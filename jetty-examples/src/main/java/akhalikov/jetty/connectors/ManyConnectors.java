package akhalikov.jetty.connectors;

import akhalikov.jetty.handlers.hellohandler.HelloHandler;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A Jetty server with multiple connectors.
 */
class ManyConnectors {
  public static void main(String[] args) throws Exception {
    // Since this example shows off SSL configuration, we need a keystore
    // with the appropriate key. These lookup of jetty.home is purely a hack
    // to get access to a keystore that we use in many unit tests and should
    // probably be a direct path to your own keystore.
    String jettyDistKeystore = "../../jetty-distribution/target/distribution/demo-base/etc/keystore";
    String keystorePath = System.getProperty("example.keystore", jettyDistKeystore);
    File keystoreFile = new File(keystorePath);
    if (!keystoreFile.exists()) {
      throw new FileNotFoundException(keystoreFile.getAbsolutePath());
    }

    // Create a basic jetty server object without declaring the port. Since
    // we are configuring connectors directly we'll be setting ports on those connectors.
    Server server = new Server();

    // HTTP Configuration
    // HttpConfiguration is a collection of configuration information appropriate for http and https.
    // The default scheme for http is <code>http</code> of course, as the default for secured http is
    // <code>https</code> but we show setting the scheme to show it can be
    // done. The port for secured communication is also set here.
    HttpConfiguration httpConfig = new HttpConfiguration();
    httpConfig.setSecureScheme("https");
    httpConfig.setSecurePort(8443);
    httpConfig.setOutputBufferSize(32768);

    // HTTP connector
    // The first server connector we create is the one for http, passing in
    // the http configuration we configured above so it can get things like the output buffer size, etc.
    ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
    http.setPort(8080);
    http.setIdleTimeout(30000);

    // SSL Context Factory for HTTPS
    // SSL requires a certificate so we configure a factory for ssl contents
    // with information pointing to what keystore the ssl connection needs
    // to know about. Much more configuration is available the ssl context,
    // including things like choosing the particular certificate out of a keystore to be used.
    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStorePath(keystoreFile.getAbsolutePath());
    sslContextFactory.setKeyStorePassword("some password");
    sslContextFactory.setKeyManagerPassword("some password");

    // HTTPS Configuration
    // A new HttpConfiguration object is needed for the next connector and you can pass the old one as an argument
    // to effectively clone the contents. On this HttpConfiguration object
    // we add a SecureRequestCustomizer which is how a new connector is able to resolve
    // the https connection before handing control over to the Jetty Server.
    HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
    httpsConfig.addCustomizer(new SecureRequestCustomizer());

    // HTTPS connector
    // We create a second ServerConnector, passing in the http configuration
    // we just made along with the previously created ssl context factory.
    // Next we set the port and a longer idle timeout.
    ServerConnector https = new ServerConnector(server,
      new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
        new HttpConnectionFactory(httpsConfig));
    https.setPort(8443);
    https.setIdleTimeout(500000);

    server.setConnectors(new Connector[] {http, https});
    server.setHandler(new HelloHandler());

    server.start();
    server.join();
  }
}
