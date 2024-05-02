package kg.inai.qrgenerator.entity;

import jakarta.persistence.*;
import kg.inai.qrgenerator.commons.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails {

    public static final AtomicLong duplicateId = new AtomicLong(1);

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String middleName;

    @Access(AccessType.FIELD)
    private boolean valid;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id"
    )
    private Group group;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "teacher"
    )
    private List<SubjectSchedule> subjectSchedules;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(List.of(new SimpleGrantedAuthority(role.getName())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isValid();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isValid();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isValid();
    }

    @Override
    public boolean isEnabled() {
        return isValid();
    }
}
