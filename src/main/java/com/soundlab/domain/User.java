package com.soundlab.domain;


import com.soundlab.domain.properties.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
  private String email;

  @Column(name = "username")
  private String username;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @Column(name = "active", columnDefinition = "boolean default false")
  private boolean active;


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
