package com.crediya.r2dbc;

import com.crediya.model.user.User;
import com.crediya.model.user.constants.LogMessages;
import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.gateways.UserRepository;
import com.crediya.r2dbc.entity.UserEntity;
import com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, Long, IUserRepository> implements UserRepository {
	private final TransactionalOperator transactionalOperator;

	public UserRepositoryAdapter(
	 IUserRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
		super(repository, mapper, d -> mapper.map(d, User.class));
		this.transactionalOperator = transactionalOperator;
	}

	@Override
	public Mono<User> save(User user) {
		if (user == null) {
			return Mono.error(new IllegalArgumentException(ValidationMessages.USER_CANNOT_BE_NULL));
		}
		log.info(LogMessages.SAVING_USER_IN_DATABASE);
		return super.save(user).as(transactionalOperator::transactional);
	}

	@Override
	public Mono<Boolean> existsByEmail(String email) {
		log.info(LogMessages.VALIDATING_EMAIL_IN_DATABASE);
		return repository.existsByEmail(email);
	}
}
