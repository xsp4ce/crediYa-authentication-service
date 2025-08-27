package com.crediya.r2dbc;

import com.crediya.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveCrudRepository<UserEntity, Long>,
 ReactiveQueryByExampleExecutor<UserEntity> {
	Mono<Boolean> existsByEmail(String email);

	Mono<Boolean> existsByDocumentNumber(String documentNumber);
}
