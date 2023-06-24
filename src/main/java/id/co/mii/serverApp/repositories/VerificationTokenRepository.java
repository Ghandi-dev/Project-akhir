package id.co.mii.serverApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long>{
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}
