package com.daichi703n.spotleft.web;

import java.util.List;

import com.daichi703n.spotleft.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ComponentScan
@Controller
@RequestMapping("/saved")
public class SavedController {

    @Autowired
    private SavedInstanceService savedInstanceService;

    @GetMapping
    public String instances(Model model) {
        List<SavedInstance> savedInstances = savedInstanceService.findAll();
        model.addAttribute("savedInstances",savedInstances);
        return "instances/saved";
    }
    
    @GetMapping("new")
    public String newSavedInstance(Model model) {
        return "saved/new";
    }
    

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        SavedInstance savedInstance = savedInstanceService.findById(id);
        model.addAttribute("savedInstance",savedInstance);
        return "saved/edit";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        SavedInstance savedInstance = savedInstanceService.findById(id);
        model.addAttribute("savedInstance",savedInstance);
        return "saved/edit";
    }

    @PostMapping
    public String create(@ModelAttribute SavedInstance savedInstance) {
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
