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
@EnableJpaRepositories(basePackages = "mis.microservices.userservice.repository", entityManagerFactoryRef = "misEntityManager", transactionManagerRef = "misTransactionManager")
public class PersistenceMISAutoConfiguration {

	@Value("${spring.datasource.jpa.hibernate.ddl-auto}")
	private String ddlAuto;

	@Primary
	@Bean(name = "mis-db")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
	}

	// misEntityManager bean
	@Primary
	@Bean(name = "misEntityManager")
	public LocalContainerEntityManagerFactoryBean misEntityManager(final EntityManagerFactoryBuilder builder,
			final @Qualifier("mis-db") DataSource dataSource) {

		return builder.dataSource(dataSource).packages("mis.microservices.userservice.model").persistenceUnit("mis-db")
				.properties(singletonMap("hibernate.hbm2ddl.auto", ddlAuto))
				.properties(singletonMap("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"))
				
				.properties(singletonMap("hibernate.physical_naming_strategy",
						"com.vladmihalcea.hibernate.type.util.CamelCaseToSnakeCaseNamingStrategy"))

				.build();
	}

	@Primary
	@Bean(name = "misTransactionManager")
	public PlatformTransactionManager misTransactionManager(
			@Qualifier("misEntityManager") EntityManagerFactory misTransactionManager) {
		return new JpaTransactionManager(misTransactionManager);
	}

}
