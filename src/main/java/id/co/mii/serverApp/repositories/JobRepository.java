package id.co.mii.serverApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverApp.models.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

}
