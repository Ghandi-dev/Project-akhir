package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.mii.serverApp.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT r FROM Employee r WHERE r.manager.id = ?1")
    public List<Employee> getByManagerId(Integer id);

    @Query("SELECT r FROM Employee r WHERE r.job.id = ?1")
    public List<Employee> getByJobId(Integer id);

    @Query("SELECT COUNT(r) FROM Employee r")
    public Integer countEmployee();
}
