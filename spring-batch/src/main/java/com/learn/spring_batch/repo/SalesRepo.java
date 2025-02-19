package com.learn.spring_batch.repo;

import com.learn.spring_batch.entities.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepo extends JpaRepository<SalesEntity,Long> {




}
