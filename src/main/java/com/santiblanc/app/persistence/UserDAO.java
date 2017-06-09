package com.santiblanc.app.persistence;

import com.santiblanc.app.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public interface UserDAO extends CrudRepository<User, Long> {
    void deleteByEmail(String email);
}
