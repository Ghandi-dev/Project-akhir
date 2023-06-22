package id.co.mii.serverApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.mii.serverApp.models.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

}
