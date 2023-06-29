package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query("SELECT r FROM Job r WHERE r.name <> 'HR' ")
    public List<Job> getAll();
}
