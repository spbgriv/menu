package net.menu.config;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by griv.
 */
@Configuration
@Import({
        DataAccessConfig.class,
        SecurityConfig.class,
        WebConfig.class
})
public class AppConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        return messageSource;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.addUser("admin", "secret");
                tomcat.addRole("admin", "manager-gui");
/*

                try {
                    tomcat.addWebapp("/manager", "/path/to/manager/app");
                }
                catch (ServletException ex) {
                    throw new IllegalStateException("Failed to add manager app", ex);
                }
*/
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
        };
    }
}
