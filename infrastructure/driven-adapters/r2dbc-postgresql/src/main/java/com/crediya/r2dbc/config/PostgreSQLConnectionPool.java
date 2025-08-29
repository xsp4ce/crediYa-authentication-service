package com.crediya.r2dbc.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

import java.time.Duration;

@Configuration
public class PostgreSQLConnectionPool {
	public static final int INITIAL_SIZE = 12;
	public static final int MAX_SIZE = 15;
	public static final int MAX_IDLE_TIME = 30;

	@Bean
	public TransactionalOperator transactionalOperator(ConnectionFactory connectionFactory) {
		return TransactionalOperator.create(new R2dbcTransactionManager(connectionFactory));
	}

	@Bean
	public ConnectionPool getConnectionConfig(PostgresqlConnectionProperties properties) {
		PostgresqlConnectionConfiguration dbConfiguration =
		 PostgresqlConnectionConfiguration.builder().host(properties.host()).port(properties.port())
			.database(properties.database()).schema(properties.schema()).username(properties.username())
			.password(properties.password()).sslMode(SSLMode.REQUIRE).build();

		ConnectionPoolConfiguration poolConfiguration =
		 ConnectionPoolConfiguration.builder().connectionFactory(new PostgresqlConnectionFactory(dbConfiguration))
			.name("api-postgres-connection-pool").initialSize(INITIAL_SIZE).maxSize(MAX_SIZE)
			.maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME)).validationQuery("SELECT 1").build();

		return new ConnectionPool(poolConfiguration);
	}
}