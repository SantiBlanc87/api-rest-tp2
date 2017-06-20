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

    List<Message> findByReceiver (User receiver);

    List<Message> findBySender (User sender);

    List<Message> findBySenderAndErasedBySender (User sender, Boolean bool);

    List<Message> findByReceiverAndErasedByReceiver (User receiver, Boolean bool);

//    @Modifying
//    @Query(value = "select * from Messages where (sender_id = ?1 and erased_by_sender = true) and (receiver_id = ?1 and erased_by_receiver = true)", nativeQuery = true)
    List<Message> findBySenderAndErasedBySenderOrReceiverAndErasedByReceiver(User s, Boolean send, User u, Boolean rec);
}
