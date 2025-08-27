package com.crediya.r2dbc;

import com.crediya.model.user.User;
import com.crediya.model.user.gateways.UserRepository;
import com.crediya.r2dbc.entity.UserEntity;
import com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, Long, IUserRepository> implements UserRepository {
	public UserRepositoryAdapter(IUserRepository repository, ObjectMapper mapper) {
		super(repository, mapper, d -> mapper.map(d, User.class));
	}

	@Override
	public Mono<User> save(User user) {
		if (user == null) {
			return Mono.error(new IllegalArgumentException("User cannot be null"));
		}
		log.info("Saving user in the database");
		return super.save(user);
	}

	@Override
	public Mono<Boolean> existsByEmail(String email) {
		log.info("Validating if email is already registered in the database");
		return repository.existsByEmail(email);
	}

	@Override
	public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
		log.info("Validating if document number is already registered in the database");
		return repository.existsByDocumentNumber(documentNumber);
	}
}