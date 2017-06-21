package com.santiblanc.app.persistence;

import com.santiblanc.app.entities.Message;
import com.santiblanc.app.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public interface MessageDAO extends CrudRepository<Message, Long> {

    List<Message> findBySenderAndErasedBySender(User sender, Boolean bool);

    List<Message> findByReceiverAndErasedByReceiver(User receiver, Boolean bool);

    List<Message> findBySenderAndErasedBySenderOrReceiverAndErasedByReceiver(User s, Boolean send, User u, Boolean rec);

}
