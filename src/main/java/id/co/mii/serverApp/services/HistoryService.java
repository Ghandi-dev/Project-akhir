package id.co.mii.serverApp.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.co.mii.serverApp.models.History;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.repositories.HistoryRepository;
import id.co.mii.serverApp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HistoryService {

    private HistoryRepository historyRepository;
    private UserRepository userRepository;

    public List<History> getAll() {
        return historyRepository.findAllByOrderByIdDesc();
    }

    public List<History> getAllByEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return historyRepository.getAllByEmployee(user.getId());
    }

    public List<History> getAllByManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return historyRepository.getAllByManager(user.getId());
    }

    public List<History> getHitoryNewProject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return historyRepository.getHistoryNewProject(
                historyRepository.getNewOvertimeId(user.getId(), PageRequest.of(0, 1)),
                user.getId());
    }

    public History getById(Integer id) {
        return historyRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "History not found!!!"));
    }

    public History create(History history) {
        return historyRepository.save(history);
    }

    public History update(Integer id, History history) {
        getById(id);
        history.setId(id);
        return historyRepository.save(history);
    }

    public History delete(Integer id) {
        History history = getById(id);
        historyRepository.delete(history);
        return history;
    }
}
