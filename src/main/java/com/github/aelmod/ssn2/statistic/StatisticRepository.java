package com.github.aelmod.ssn2.statistic;

import com.github.aelmod.ssn2.BaseRepository;
import com.github.aelmod.ssn2.conversation.message.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class StatisticRepository extends BaseRepository<Message, Integer> {

    public StatisticRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Long> getMessagesFromCertainTime(Date date) {
        return entityManager.createQuery("SELECT COUNT(message) FROM Message AS message WHERE message.creationTime > :date GROUP BY message.user", Long.class)
                .setParameter("date", date)
                .getResultList();
    }
}
