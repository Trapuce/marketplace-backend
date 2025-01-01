package com.trapuce.marketplace.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ajouter les rôles en fonction du champ is_professional ou d'autres critères
        if (user.getIs_professional() != null && user.getIs_professional()) {
            return List.of(new SimpleGrantedAuthority("ROLE_PROFESSIONAL"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Vous pouvez ici ajouter une logique de vérification si nécessaire
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Idem
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Vous pouvez ajouter une logique si les credentials expirent
    }

    @Override
    public boolean isEnabled() {
        return true; // Vérifiez l'activation de l'utilisateur si besoin
    }

    // Getter et Setter pour l'objet User
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
