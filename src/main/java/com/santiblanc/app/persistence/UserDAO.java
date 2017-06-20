package com.santiblanc.app.persistence;

import com.santiblanc.app.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public interface UserDAO extends CrudRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("delete from User u where u.email = ?1")
    void deleteByEmail(String email);

    User findByEmailAndPass(String email, String pass);

    User findByEmail(String email);

    List<User> findByNombre(String nombre);
}
