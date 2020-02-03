package com.daichi703n.spotleft.web.api.v1;

import java.util.List;

import com.daichi703n.spotleft.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan
@RestController
@RequestMapping("/api/v1/saved")
public class ApiSavedController {

    @Autowired
    private SavedInstanceService savedInstanceService;

    @GetMapping
    public List<SavedInstance> instances() {
        List<SavedInstance> savedInstances = savedInstanceService.findAll();
        return savedInstances;
    }
    
    @GetMapping("new")
    public String newSavedInstance() {
        return "Please POST to {id}";
    }
    

    @GetMapping("{id}/edit")
    public SavedInstance edit(@PathVariable Long id) {
        SavedInstance savedInstance = savedInstanceService.findById(id);
        return savedInstance;
    }

    @GetMapping("{id}")
    public SavedInstance show(@PathVariable Long id) {
        SavedInstance savedInstance = savedInstanceService.findById(id);
        return savedInstance;
    }

    @PostMapping
    public String create(@RequestBody SavedInstance savedInstance) {
        savedInstanceService.save(savedInstance);
        return "redirect:/saved";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute SavedInstance savedInstance) {
        savedInstance.setId(id);
        savedInstanceService.save(savedInstance);
        return "redirect:/saved";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable Long id) {
        savedInstanceService.delete(id);
        return "redirect:/saved";
    }
}
