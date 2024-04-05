package com.soundlab.domain;


import com.soundlab.domain.properties.Gender;
import com.soundlab.domain.properties.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Model for the representation of a user in the system
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"email"}
        )
    }
)
public class User extends AuditModel implements UserDetails {

  @Id
  @NotNull(message = "La mail è richiesta")
  @NotBlank(message = "La mail è richiesta")
  private String email;

  @NotNull(message = "Lo username è richiesto")
  @NotBlank(message = "Lo username è richiesto")
  @Column(name = "username")
  private String username;

  @NotNull(message = "La password è richiesta")
  @NotBlank(message = "La password è richiesta")
  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @Column(name = "birth")
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date birth;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "active", columnDefinition = "boolean default true")
  private boolean active;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<Listening> listenings;

  @OneToOne
  @JoinColumn(name = "library_id")
  private Library library;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public String getName(){ return username; }

  @Override
  public boolean isAccountNonExpired() {
    return active;
  }

  @Override
  public boolean isAccountNonLocked() {
    return active;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return active;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}
