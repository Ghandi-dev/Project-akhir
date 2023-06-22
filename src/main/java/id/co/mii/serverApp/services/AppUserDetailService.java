package id.co.mii.serverApp.services;

import id.co.mii.serverApp.models.AppUserDetail;
import id.co.mii.serverApp.models.User;
import id.co.mii.serverApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepository
      .findByUsernameOrEmployee_Email(username, username)
      .orElseThrow(() ->
        new UsernameNotFoundException("Username or Email incorrect!!!")
      );

    return new AppUserDetail(user);
  }
}
