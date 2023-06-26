package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.mii.serverApp.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query("SELECT r FROM Project r WHERE r.manager.id = ?1")
    public List<Project> GetByManagerId(Integer id);

    @Query("SELECT r.projects2 FROM Employee r WHERE r.id = ?1")
    public List<Project> GetByEmployeeId(Integer id);
}
