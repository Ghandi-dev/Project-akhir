package id.co.mii.serverApp.services;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.models.VerificationToken;
import id.co.mii.serverApp.repositories.VerificationTokenRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VerificationTokenService {
    
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public VerificationToken findByToken(String token){
        return verificationTokenRepository.findByToken(token);
    }

    @Transactional
    public VerificationToken findByUser(User user){
        return verificationTokenRepository.findByUser(user);
    }

    @Transactional
    public void save(User user, String token){
        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationToken.setExpiryDate(calculateExpiryDate(24*60));
        verificationTokenRepository.save(verificationToken);
    }

    private Timestamp calculateExpiryDate(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}    
