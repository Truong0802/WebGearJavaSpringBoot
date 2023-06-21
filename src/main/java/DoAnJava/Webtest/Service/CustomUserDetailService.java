package DoAnJava.Webtest.Service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Entity.role;
import DoAnJava.Webtest.Repository.TAI_KHOAN_Repository;
import DoAnJava.Webtest.Repository.role_Repository;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private TAI_KHOAN_Repository tai_KHOAN_Repository;
    @Autowired
    private role_Repository role_Repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TAI_KHOAN u = tai_KHOAN_Repository.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Không thể tìm thấy người dùng");
        }
        Set<GrantedAuthority> authorities = u.getRoles().stream()
                .map((role_id) -> new SimpleGrantedAuthority(role_id.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                username,
                u.getPassword(),
                authorities);
    }
}
