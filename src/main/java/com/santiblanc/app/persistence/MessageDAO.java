package com.santiblanc.app.persistence;

import com.santiblanc.app.entities.Message;
import com.santiblanc.app.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public interface MessageDAO extends CrudRepository<Message, Long> {
    List<Message> findByReceiver (Long id);
    List<Message> findBySender (Long id);
    List<Message> findByReceiverAndErased (Long id, Boolean bool);
}
