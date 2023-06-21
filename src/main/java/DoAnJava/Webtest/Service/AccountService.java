package DoAnJava.Webtest.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Entity.role;
import DoAnJava.Webtest.Repository.TAI_KHOAN_Repository;
import DoAnJava.Webtest.Repository.role_Repository;
import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    TAI_KHOAN_Repository tai_KHOAN_Repository;
    @Autowired
    role_Repository role_Repository;

    public void add(TAI_KHOAN newUser) {
        tai_KHOAN_Repository.save(newUser);
    }

    @Transactional
    public void addUserWithRole(TAI_KHOAN user) {
        TAI_KHOAN savedUser = tai_KHOAN_Repository.save(user);

        // Tìm role có ID là 1
        role role = role_Repository.findById(1).orElseThrow(() -> new RuntimeException("Role not found"));
        savedUser.getRoles().add(role);
        tai_KHOAN_Repository.save(savedUser);
    }
}
