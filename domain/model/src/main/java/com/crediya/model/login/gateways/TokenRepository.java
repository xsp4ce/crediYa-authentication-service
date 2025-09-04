package com.crediya.model.login.gateways;

import com.crediya.model.login.Token;
import com.crediya.model.user.User;
import reactor.core.publisher.Mono;

public interface TokenRepository {
	Mono<Token> generateFor(User user);
}
