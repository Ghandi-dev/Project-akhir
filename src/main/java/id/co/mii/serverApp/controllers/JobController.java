package id.co.mii.serverApp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverApp.models.Job;
import id.co.mii.serverApp.services.JobService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/job")
@AllArgsConstructor
public class JobController {
    private JobService jobService;

    @GetMapping
    public List<Job> getAll() {
        return jobService.getAll();
    }

    @GetMapping("/{id}")
    public Job getById(@PathVariable Integer id) {
        return jobService.getById(id);
    }

    @PostMapping
    public Job create(@RequestBody Job job) {
        return jobService.create(job);
    }

    @PutMapping("/{id}")
    public Job update(
            @PathVariable Integer id,
            @RequestBody Job job) {
        return jobService.update(id, job);
    }

    @DeleteMapping("/{id}")
    public Job delete(@PathVariable Integer id) {
        return jobService.delete(id);
    }
}