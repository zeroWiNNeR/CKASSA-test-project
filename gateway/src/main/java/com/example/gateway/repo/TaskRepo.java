package com.example.gateway.repo;

import com.example.gateway.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.createTime < ?1 AND t.status = 'INQUEUE'")
    List<Task> getTasksByCreateTimeLessThan(LocalDateTime time);

}
