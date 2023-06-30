package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.mii.serverApp.models.Employee;
import id.co.mii.serverApp.models.History;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public List<History> findAllByOrderByIdDesc();

    @Query("SELECT r FROM Employee r WHERE r.manager.id = ?1")
    public List<Employee> getByManagerId(Integer id);

    @Query("SELECT r FROM Employee r WHERE r.job.id = ?1")
    public List<Employee> getByJobId(Integer id);

    @Query("SELECT r FROM Employee r WHERE r.job.name = 'Manager' ")
    public List<Employee> getManager();

    @Query("SELECT r FROM Employee r WHERE r.job.name = 'HR' ")
    public List<Employee> getHr();

    @Query("SELECT r FROM Employee r WHERE r.job.name <> 'Hr' ORDER BY r.id DESC")
    public List<Employee> getAll();

    @Query("SELECT COUNT(r) FROM Employee r")
    public Integer countEmployee();
}
