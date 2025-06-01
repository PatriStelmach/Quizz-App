package com.pjatk.QuizzApp.User;

import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal
{

    public User(String username, String password, String email)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @NotEmpty(message = "email can't be null")
    @NotBlank(message = "email can't be blank")
    @Column(unique = true, nullable = false)
    @Email(message = "Invalid email address")
    private String email;

    private String avName;
    private String avType;
    @Lob
    private byte[] avatar;
    private String bio;
    private boolean isActive = true;
    private boolean accountLocked;
    private boolean enabled;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;


    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private UserLevel userLevel = UserLevel.NEWBIE;

    private Integer streak = 0;
    private Integer score = 0;

    @Column(name = "finished")
    private Integer finished_amount = 0;


    @ManyToMany(mappedBy = "finishedByUsers")
    private Set<Quiz> finished_quizzes = new HashSet<>();

    @ManyToMany(mappedBy = "userScore")
    private Set<Quiz> quizScore = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friendsList = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<Quiz> quizzesCreated = new HashSet<>();

    public void addRole(Role role)
    {
        roles.add(role);
    }
    public void deleteRole(Role role)
    {
        roles.remove(role);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
