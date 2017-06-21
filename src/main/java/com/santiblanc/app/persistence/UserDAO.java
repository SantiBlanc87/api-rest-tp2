package com.santiblanc.app.persistence;

import com.santiblanc.app.entities.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public interface UserDAO extends CrudRepository<User, Long> {

    User findByEmailAndPass(String email, String pass);

    User findByEmail(String email);

    List<User> findByNombre(String nombre);

}
