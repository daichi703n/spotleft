package com.daichi703n.spotleft.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daichi703n.spotleft.domain.SavedInstances;
import com.daichi703n.spotleft.repository.SavedInstancesRepository;

@Service
public class SavedInstancesService {

    @Autowired
    private SavedInstancesRepository savedInstancesRepository;

    public List<SavedInstances> findAll() {
        return savedInstancesRepository.findAll();
    }

    public SavedInstances findById(Long id) {
        return savedInstancesRepository.findById(id).orElse(null);
    }

    public SavedInstances save(SavedInstances savedInstances) {
        return savedInstancesRepository.save(savedInstances);
    }

    public void delete(Long id) {
        savedInstancesRepository.deleteById(id);
    }
}
