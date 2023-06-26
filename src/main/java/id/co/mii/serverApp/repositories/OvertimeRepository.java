package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.co.mii.serverApp.models.Overtime;

public interface OvertimeRepository extends JpaRepository<Overtime,Integer>{
    @Query("SELECT r FROM Overtime r WHERE r.employee.id = ?1")
    public List<Overtime> getByEmployeeId(Integer id);
}
