package com.springsecurity.practica.User.Entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.springsecurity.practica.Domain.Person.Entity.Person;
import com.springsecurity.practica.User.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase permite mapear el resultado de la base de datos, como una Object de Java que contiene los datos del
 * usuario, pero a su vez se le implementa la Interfaz UserDetails, ya que por medio de esta interfaz, Spring Security
 * representa el usuario de la BD, además permite agregar los permisos que este usuario pueda tener.
 */
@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
//uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})} permite especificar que el unico campo que no se puede repetir es el username
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@PrimaryKeyJoinColumn(name = "user_id")
public class User extends Person implements UserDetails{

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Permite definir los permisos que el usuario debe tener
     * @return una lista de permisos + el rol del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

         List<GrantedAuthority> listPermission =
             role.getAllPermissions()
                 .stream()
                 .map(permission -> new SimpleGrantedAuthority(permission.name()))
                 .collect(Collectors.toList());

         listPermission.add(new SimpleGrantedAuthority("ROLE_"+role.name()));

       return listPermission;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
