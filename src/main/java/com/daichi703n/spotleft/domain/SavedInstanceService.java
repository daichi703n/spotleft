package com.daichi703n.spotleft.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.daichi703n.spotleft.domain.SavedInstance;
import com.daichi703n.spotleft.repository.SavedInstanceRepository;

@Service
public class SavedInstanceService {

    @Autowired
    private SavedInstanceRepository savedInstanceRepository;

    public List<SavedInstance> findAll() {
        return savedInstanceRepository.findAll(new Sort(Direction.ASC, "deployment").and(new Sort(Direction.ASC, "name")));
    }

    public SavedInstance findById(Long id) {
        return savedInstanceRepository.findById(id).orElse(null);
    }

    public SavedInstance save(SavedInstance savedInstance) {
        return savedInstanceRepository.save(savedInstance);
    }

    public void delete(Long id) {
        savedInstanceRepository.deleteById(id);
    }
}
