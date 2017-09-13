package org.superbiz.moviefun;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author wuyf
 */
@Configuration
public class DbConfig {

    private static LocalContainerEntityManagerFactoryBean buildEntityManagerFactoryBean(DataSource dataSource,
                                                                                        HibernateJpaVendorAdapter jpaVendorAdapter,
                                                                                        String unitName) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(DbConfig.class.getPackage().getName());
        factoryBean.setPersistenceUnitName(unitName);
        return factoryBean;
    }

    private static DataSource buildDataSource(String url, String username, String password) {
        System.out.println("-------------url " + url);
        System.out.println("-------------username " + username);
        System.out.println("-------------password " + password);

        DataSource dataSource = DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();

        return createConnectionPool(dataSource);
    }

    private static DataSource createConnectionPool(DataSource dataSource) {
        HikariConfig config = new HikariConfig();
        config.setDataSource(dataSource);
        return new HikariDataSource(config);
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        adapter.setGenerateDdl(true);
        adapter.setDatabase(Database.MYSQL);
        return adapter;
    }

    @Configuration
    public static class Movies {

        @Value("${moviefun.datasources.movies.url}")
        String url;
        @Value("${moviefun.datasources.movies.username}")
        String username;
        @Value("${moviefun.datasources.movies.password}")
        String password;

        @Bean(name = "moviesDs")
        public DataSource moviesDataSource() {
            return buildDataSource(url, username, password);
        }

        @Bean(name = "moviesEntityManager")
        LocalContainerEntityManagerFactoryBean moviesEntityManagerFactoryBean(@Qualifier("moviesDs") DataSource moviesDataSource, HibernateJpaVendorAdapter jpaVendorAdapter) {
            System.out.println("---------moviesEntityManager:" + moviesDataSource.toString());
            return buildEntityManagerFactoryBean(moviesDataSource, jpaVendorAdapter, "movies-unit");
        }

        @Bean
        PlatformTransactionManager moviesTransactionManager(@Qualifier("moviesEntityManager") LocalContainerEntityManagerFactoryBean factoryBean) {
            return new JpaTransactionManager(factoryBean.getObject());
        }


    }

    @Configuration
    public static class Albums {

        @Value("${moviefun.datasources.albums.url}")
        String url;
        @Value("${moviefun.datasources.albums.username}")
        String username;
        @Value("${moviefun.datasources.albums.password}")
        String password;

        @Bean(name = "albumsDs")
        public DataSource albumsDataSource() {
            return buildDataSource(url, username, password);
        }

        @Bean(name = "albumsEntityManager")
        public LocalContainerEntityManagerFactoryBean albumsEntityManagerFactoryBean(@Qualifier("albumsDs") DataSource albumsDataSource, HibernateJpaVendorAdapter jpaVendorAdapter) {
            System.out.println("---------moviesEntityManager:" + albumsDataSource.toString());
            return buildEntityManagerFactoryBean(albumsDataSource, jpaVendorAdapter, "albums-unit");
        }

        @Bean
        public PlatformTransactionManager albumsTransactionManager(@Qualifier("albumsEntityManager") LocalContainerEntityManagerFactoryBean factoryBean) {
            return new JpaTransactionManager(factoryBean.getObject());
        }

    }

}
