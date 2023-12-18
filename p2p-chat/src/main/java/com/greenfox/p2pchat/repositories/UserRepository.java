package com.greenfox.p2pchat.repositories;

import com.greenfox.p2pchat.models.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, String> {
}
