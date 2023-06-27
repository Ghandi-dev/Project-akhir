package id.co.mii.serverApp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverApp.models.Overtime;
import id.co.mii.serverApp.services.OvertimeService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/overtime")
@AllArgsConstructor
public class OvertimeController {
    private OvertimeService overtimeService;

    @GetMapping
    public List<Overtime> getAll() {
        return overtimeService.getAll();
    }

    @GetMapping("/{id}")
    public Overtime getById(@PathVariable Integer id) {
        return overtimeService.getById(id);
    }

    @PostMapping
    public Overtime create(@RequestBody Overtime overtime) {
        return overtimeService.create(overtime);
    }

    @PutMapping("/{id}")
    public Overtime update(
            @PathVariable Integer id,
            @RequestBody Overtime overtime) {
        return overtimeService.update(id, overtime);
    }

    @DeleteMapping("/{id}")
    public Overtime delete(@PathVariable Integer id) {
        return overtimeService.delete(id);
    }

    @PutMapping("/approve/{id}")
    public Overtime approveOvertime(@PathVariable Integer id, @RequestParam("projectId") Integer projectId,
            @RequestParam("role") String role) {
        return overtimeService.approveOvertime(id, projectId, role);
    }

    @PutMapping("/reject/{id}")
    public Overtime rejectOvertime(@PathVariable Integer id, @RequestParam("projectId") Integer projectId,
            @RequestParam("role") String role) {
        return overtimeService.rejectOvertime(id, projectId, role);
    }
}