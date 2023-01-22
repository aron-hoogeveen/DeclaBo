package ch.bolkhuis.declabo.user;

import org.springframework.stereotype.Repository;

// this class is created for convenience. The user does not need to type UserRepository<User>
// anymore
@Repository
public interface UserRepository extends BaseUserRepository<User> {
}
