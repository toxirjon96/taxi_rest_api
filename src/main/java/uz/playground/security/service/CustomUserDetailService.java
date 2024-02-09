package uz.playground.security.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.playground.security.entity.User;
import uz.playground.security.repository.UserRepository;
import uz.playground.security.security.UserPrincipal;
import javax.transaction.Transactional;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(userName)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with phone number : " + userName)
                );
        return UserPrincipal.create(user);
    }
    @Transactional
    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserPrincipal.create(user);
    }
}