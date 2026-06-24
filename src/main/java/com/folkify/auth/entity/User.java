package com.folkify.auth.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Plan plan = Plan.FREE;

    @Column(name = "apple_sub", unique = true)
    private String appleSub;

    public User() {}

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role != null ? builder.role : Role.USER;
        this.plan = builder.plan != null ? builder.plan : Plan.FREE;
        this.appleSub = builder.appleSub;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name;
        private String email;
        private String password;
        private Role role;
        private Plan plan;
        private String appleSub;

        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public Builder plan(Plan plan) { this.plan = plan; return this; }
        public Builder appleSub(String appleSub) { this.appleSub = appleSub; return this; }
        public User build() { return new User(this); }
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public Plan getPlan() { return plan; }
    public String getAppleSub() { return appleSub; }

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public void setAppleSub(String appleSub) { this.appleSub = appleSub; }

    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
