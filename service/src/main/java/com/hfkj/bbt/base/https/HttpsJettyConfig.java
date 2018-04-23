package com.hfkj.bbt.base.https;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HttpsJettyConfig {

    @Value("${server.port}")
    private int httpPort;

    @Value("${https.port}")
    private int httpsPort;

    @Value("${https.key-store-path}")
    private String keyPath;

    @Value("${https.key-password}")
    private String keyPassword;

    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof JettyEmbeddedServletContainerFactory) {
                    customizeJetty((JettyEmbeddedServletContainerFactory) container);
                }
            }

            private void customizeJetty(JettyEmbeddedServletContainerFactory container) {

                container.addServerCustomizers(new JettyServerCustomizer() {

                    @Override
                    public void customize(Server server) {
                        ServerConnector connector = new ServerConnector(server);
                        connector.setPort(httpPort);
                        // HTTPS
                        SslContextFactory sslContextFactory = new SslContextFactory();
                        sslContextFactory.setKeyStorePath(keyPath);
                        sslContextFactory.setKeyStorePassword(keyPassword);

                        HttpConfiguration https = new HttpConfiguration();
                        https.addCustomizer(new SecureRequestCustomizer());

                        ServerConnector sslConnector = new ServerConnector(
                                server,
                                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                                new HttpConnectionFactory(https));
                        sslConnector.setPort(httpsPort);

                        server.setConnectors(new Connector[]{connector, sslConnector});

                    }

                });
            }
        };

    }

}