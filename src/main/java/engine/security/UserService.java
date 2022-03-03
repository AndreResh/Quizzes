package engine.security;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public User saveUser(User user){
        return repository.save(user);
    }
    public User findUserByEmail(String email){
        return repository.findAllByEmail(email);
    }
}
