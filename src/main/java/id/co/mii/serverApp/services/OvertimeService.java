package id.co.mii.serverApp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.Overtime;
import id.co.mii.serverApp.repositories.OvertimeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OvertimeService {
    
    private OvertimeRepository overtimeRepository;

    public List<Overtime> getAll() {
        return overtimeRepository.findAll();
    }

    public Overtime getById(Integer id) {
        return overtimeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Overtime not found!!!"));
    }

    public Overtime create(Overtime overtime) {
        return overtimeRepository.save(overtime);
    }

    public Overtime update(Integer id, Overtime overtime) {
        getById(id);
        overtime.setId(id);
        return overtimeRepository.save(overtime);
    }

    public Overtime delete(Integer id) {
        Overtime overtime = getById(id);
        overtimeRepository.delete(overtime);
        return overtime;
    }
}
