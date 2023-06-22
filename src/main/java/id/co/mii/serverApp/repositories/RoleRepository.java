package id.co.mii.serverApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverApp.models.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{
    
}
