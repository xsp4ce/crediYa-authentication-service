package com.crediya.r2dbc;

import com.crediya.model.user.User;
import com.crediya.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveCrudRepository<UserEntity, Long>,
 ReactiveQueryByExampleExecutor<UserEntity> {
	Mono<User> findByEmail(String email);

	Mono<User> findByDocumentNumber(String documentNumber);
}
