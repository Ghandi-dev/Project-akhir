package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.mii.serverApp.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query("SELECT r FROM Project r WHERE r.manager.id = ?1 ORDER BY r.id DESC")
    public List<Project> GetByManagerId(Integer id);

    @Query("SELECT r.projects2 FROM Employee r WHERE r.id = ?1 ORDER BY r.id DESC")
    public List<Project> GetByEmployeeId(Integer id);

    @Query("SELECT COUNT(r) FROM Project r")
    public Integer countAll();

    @Query("SELECT COUNT(r) FROM Project r WHERE r.manager.id = ?1")
    public Integer countByManager(Integer id);

    @Query(value = "SELECT COUNT(project_id) FROM tb_team_project u WHERE u.employee_id = ?1", nativeQuery = true)
    public Integer countByEmployee(Integer id);
}
