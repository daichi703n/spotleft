package com.daichi703n.spotleft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daichi703n.spotleft.domain.SavedInstances;

@Repository
public interface SavedInstancesRepository extends JpaRepository<SavedInstances, Long> {

}
