package id.co.mii.serverApp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.Job;
import id.co.mii.serverApp.repositories.JobRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobService {
    private JobRepository jobRepository;

    public List<Job> getAll() {
        return jobRepository.getAll();
    }

    public Job getById(Integer id) {
        return jobRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job not found!!!"));
    }

    public Job create(Job job) {
        return jobRepository.save(job);
    }

    public Job update(Integer id, Job job) {
        getById(id);
        job.setId(id);
        return jobRepository.save(job);
    }

    public Job delete(Integer id) {
        Job job = getById(id);
        jobRepository.delete(job);
        return job;
    }
}
