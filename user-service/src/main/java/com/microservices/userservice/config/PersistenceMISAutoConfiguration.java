package com.microservices.userservice.config;

import static java.util.Collections.singletonMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@PropertySource({ "classpath:application-${spring.profiles.active}.properties" })
@EnableJpaRepositories(basePackages = "com.microservices.userservice.repository", entityManagerFactoryRef = "comEntityManager", transactionManagerRef = "comTransactionManager")
public class PersistenceMISAutoConfiguration {

	@Value("${spring.datasource.jpa.hibernate.ddl-auto}")
	private String ddlAuto;

	@Primary
	@Bean(name = "user-db")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
	}

	// misEntityManager bean
	@Primary
	@Bean(name = "comEntityManager")
	public LocalContainerEntityManagerFactoryBean comEntityManager(final EntityManagerFactoryBuilder builder,
			final @Qualifier("user-db") DataSource dataSource) {

		return builder.dataSource(dataSource).packages("com.microservices.userservice.model")
				.persistenceUnit("user-db").properties(singletonMap("hibernate.hbm2ddl.auto", ddlAuto))
				.properties(singletonMap("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"))

				.properties(singletonMap("hibernate.physical_naming_strategy",
						"com.vladmihalcea.hibernate.type.util.CamelCaseToSnakeCaseNamingStrategy"))

				.build();
	}

	@Primary
	@Bean(name = "comTransactionManager")
	public PlatformTransactionManager comTransactionManager(
			@Qualifier("comEntityManager") EntityManagerFactory misTransactionManager) {
		return new JpaTransactionManager(misTransactionManager);
	}

}
