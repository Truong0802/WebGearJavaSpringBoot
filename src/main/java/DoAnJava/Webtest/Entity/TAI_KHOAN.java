package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TAI_KHOAN")
public class TAI_KHOAN {
    @Id
    @NotBlank(message = "Ten dang nhap da duoc su dung")
    @Size(max = 50, message = "Ten dang nhap it hon 50 ky tu")
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
    private Collection<NHAN_VIEN> NHAN_VIENs;
    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
    private Collection<KHACH_HANG> KHACH_HANGS;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<role> roles = new HashSet<>();
}
