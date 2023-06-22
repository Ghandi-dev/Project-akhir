package id.co.mii.serverApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverApp.models.History;

public interface HistoryRepository extends JpaRepository<History,Integer>{
    
}
