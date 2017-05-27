package shop.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import shop.controllers.AdminController;
import shop.models.DAO.AdminDao;
import shop.models.DAO.AdminDaoImpl;
import shop.models.DAO.ProductDaoImpl;
import shop.models.DAO.ShareDaoImpl;
import shop.service.*;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

//  @Value("${spring.mvc.view.prefix}")
//  public String PREFIX;
//
//  @Value("${spring.mvc.view.suffix}")
//  public String SUFFIX;


  @Value("${db.driver}")
  private String DB_DRIVER;
  
  @Value("${db.password}")
  private String DB_PASSWORD;
  
  @Value("${db.url}")
  private String DB_URL;
  
  @Value("${db.username}")
  private String DB_USERNAME;

  @Value("${hibernate.dialect}")
  private String HIBERNATE_DIALECT;
  
  @Value("${hibernate.show_sql}")
  private String HIBERNATE_SHOW_SQL;
  
  @Value("${hibernate.hbm2ddl.auto}")
  private String HIBERNATE_HBM2DDL_AUTO;

  @Value("${entitymanager.packagesToScan}")
  private String ENTITYMANAGER_PACKAGES_TO_SCAN;


  
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(DB_DRIVER);
    dataSource.setUrl(DB_URL);
    dataSource.setUsername(DB_USERNAME);
    dataSource.setPassword(DB_PASSWORD);
    return dataSource;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource());
    sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
    Properties hibernateProperties = new Properties();
    hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
    hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
    hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
    sessionFactoryBean.setHibernateProperties(hibernateProperties);
    
    return sessionFactoryBean;
  }



  /*@Bean(name = "internalResourceViewResolver")
  public InternalResourceViewResolver setupViewResolver()  {
    InternalResourceViewResolver resolver =  new InternalResourceViewResolver();
    resolver.setPrefix(PREFIX);
    resolver.setSuffix(SUFFIX);
    resolver.setViewClass(JstlView.class);

    return resolver;
  }*/



  @Bean(name = "adminService")
  public AdminService getAdminService() {
    return new AdminServiceImpl(new AdminDaoImpl());
  }

  @Bean(name = "productService")
  public ProductService getProductService() {
    return new ProductServiceImpl(new ProductDaoImpl());
  }

  @Bean(name = "shareService")
  public ShareService getShareService() {
    return new ShareServiceImpl(new ShareDaoImpl());
  }

} // class DatabaseConfig
