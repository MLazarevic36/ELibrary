package ues.elibrary.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ues.elibrary.ebook.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

}
