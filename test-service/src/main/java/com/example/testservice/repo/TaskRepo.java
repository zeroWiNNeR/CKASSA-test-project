package com.example.testservice.repo;

import com.example.testservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created by Aleksei Vekovshinin on 25.11.2020
 */
@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    Task getById(Long id);

}
