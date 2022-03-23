package com.edu.hutech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.edu.hutech.repository"})
public class JPAConfig {
    //config generate table in mySQL
    //config mapping, use entityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em =new LocalContainerEntityManagerFactoryBean();
        //set connection info
        em.setDataSource(dataSource());
        //declare entity, unitName from persistence.xml
        em.setPersistenceUnitName("persistence-data");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        //set mode(drop-create table, update table, ...)
        em.setJpaProperties(additionalProperties());
        return em;
    }
    //use entityTransaction & persistence
    //ngoài khai báo annotation phải gọi 2 bean này nx
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
    @Bean //cấu hình ở file persistence.xml
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    // function
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3307/mvcdb");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        //properties.setProperty("hibernate.hbm2ddl.auto","create-drop"); //for the first time, chú ý phải tạo database rỗng trước
        //properties.setProperty("hibernate.hbm2ddl.auto","create");
        properties.setProperty("hibernate.hbm2ddl.auto","none");
        properties.setProperty("hibernate.enable_lazy_load_no_trans","true");
        return properties;
    }
}
