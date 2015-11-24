package net.menu.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.SessionFactory;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by griv.
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DataAccessConfig {

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Value("classpath:sql/schema.sql")
    private org.springframework.core.io.Resource H2_SCHEMA_SCRIPT;

    @Value("classpath:sql/data.sql")
    private org.springframework.core.io.Resource H2_DATA_SCRIPT;


    @Bean
    @ConditionalOnProperty(name = "use.embedded.db")
    public DataSource embeddedDataSource() {
        return createH2DataSource();
    }


    @Bean(destroyMethod="close")
    @ConditionalOnProperty(name = "use.embedded.db", havingValue = "false")
    public DataSource dataSource() {
        return standaloneDataSource();
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws Exception {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[]{"net.menu.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                //setProperty("hibernate.default_schema", "public");
                //setProperty("hibernate.globally_quoted_identifiers", "true");
                //setProperty("hibernate.hbm2ddl.import_files", "/sql/data.sql");
                setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
            }
        };
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) throws Exception {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }

    private DataSource standaloneDataSource() {
        log.info("Configure standalone DataSource");
        HikariDataSource ds = new HikariDataSource();

        ds.setMaximumPoolSize(15);
        ds.setDriverClassName(env.getRequiredProperty("spring.driverClass"));
        ds.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        ds.setUsername(env.getRequiredProperty("spring.datasource.username"));
        ds.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return ds;
    }

    private DataSource createH2DataSource() {
        log.info("Configure h2 DataSource");
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(env.getRequiredProperty("spring.datasource.url"));
        jdbcDataSource.setUser(env.getRequiredProperty("spring.datasource.username"));
        jdbcDataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));

        return jdbcDataSource;
    }

    @PostConstruct
    protected void initialize() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(H2_SCHEMA_SCRIPT);
        populator.addScript(H2_DATA_SCRIPT);
        populator.setContinueOnError(true);
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @PostConstruct
    public void startDBManager() {
        System.getProperties().put("java.awt.headless", true);
        System.getProperties().put("sun.awt.disablegrab", true);
        DatabaseManagerSwing.main(new String[]{
                "--url", env.getRequiredProperty("spring.datasource.url"),
                "--user", env.getRequiredProperty("spring.datasource.username"),
                "--password", env.getRequiredProperty("spring.datasource.password")});

    }

}
