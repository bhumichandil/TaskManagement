package com.task.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.Entity.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {

}