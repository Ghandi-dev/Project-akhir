package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.co.mii.serverApp.models.Overtime;

public interface OvertimeRepository extends JpaRepository<Overtime, Integer> {
    @Query("SELECT r FROM Overtime r WHERE r.employee.id = ?1 ORDER BY r.id DESC")
    public List<Overtime> getByEmployeeId(Integer id);

    @Query("SELECT r FROM Overtime r WHERE r.employee.manager.id = ?1 and r.status = 'PROCESS' ORDER BY r.id DESC")
    public List<Overtime> getByManagerId(Integer id);

    @Query("SELECT r FROM Overtime r WHERE r.status = 'APPROVED_MANAGER' ORDER BY r.id DESC")
    public List<Overtime> getAllForHr();

    @Query("SELECT COUNT(r) FROM Overtime r")
    public Integer countAll();

    @Query("SELECT COUNT(r) FROM Overtime r WHERE r.employee.id =?1")
    public Integer countByEmployee(Integer id);

    @Query("SELECT COUNT(r) FROM Overtime r WHERE r.employee.manager.id =?1")
    public Integer countByManager(Integer id);

}
