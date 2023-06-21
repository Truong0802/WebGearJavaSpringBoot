package DoAnJava.Webtest.Entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class UserRole implements Serializable {
    @Column
    private String username;

    @Column
    private Integer role_id;
}
