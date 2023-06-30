package id.co.mii.serverApp.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.co.mii.serverApp.models.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    public List<History> findAllByOrderByIdDesc();

    @Query("SELECT r FROM History r WHERE r.overtime.id = ?1 and r.overtime.employee.id =?2 ORDER BY r.overtime.id  DESC")
    public List<History> getHistoryNewProject(Integer id, Integer employeeId);

    @Query("SELECT r FROM History r WHERE r.overtime.employee.id =?1 ORDER BY r.id  DESC")
    public List<History> getAllByEmployee(Integer id);

    @Query("SELECT r FROM History r WHERE r.overtime.employee.manager.id =?1 ORDER BY r.id  DESC")
    public List<History> getAllByManager(Integer id);

    @Query("SELECT r.overtime.id FROM History r ORDER BY r.id DESC ")
    public Integer getNewOvertimeId(PageRequest pageRequest);
}
