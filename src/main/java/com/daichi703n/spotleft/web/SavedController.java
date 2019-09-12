package com.daichi703n.spotleft.web;

import java.util.ArrayList;
import java.util.List;

import com.daichi703n.spotleft.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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
    
    // @GetMapping("new")
    // public String newSavedInstance(Model model) {
    //     return "saved/new";
    // }
    

    // @GetMapping("{id}/edit")
    // public String edit(@PathVariable Long id, Model model) {
    //     SavedInstance savedInstance = savedInstanceService.findById(id);
    //     model.addAttribute("savedInstance",savedInstance);
    //     return "saved/edit";
    // }

    // @GetMapping("{id}")
    // public String show(@PathVariable Long id, Model model) {
    //     SavedInstance savedInstance = savedInstanceService.findById(id);
    //     model.addAttribute("savedInstance",savedInstance);
    //     return "saved/show";
    // }

    // @PostMapping
    // public String create(@ModelAttribute SavedInstance savedInstance) {
    //     playerService.save(player);
    //     return "redirect:/players";
    // }

    // @PutMapping("{id}")
    // public String update(@PathVariable Long id, @ModelAttribute Player player) {
    //     player.setId(id);
    //     playerService.save(player);
    //     return "redirect:/players";
    // }

    // @DeleteMapping("{id}")
    // public String destroy(@PathVariable Long id) {
    //     playerService.delete(id);
    //     return "redirect:/players";
    // }
}
