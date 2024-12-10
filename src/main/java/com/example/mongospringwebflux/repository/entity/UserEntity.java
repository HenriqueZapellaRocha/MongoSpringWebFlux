package com.example.mongospringwebflux.repository.entity;

import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Document( collection = "users" )
@Data
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    private String id;
    @Indexed( unique = true )
    private String login;

    private String password;
    private UserRoles role;

    @Indexed( unique = true )
    private String storeId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Autoridade do usuário: " + this.role);  // Adicione este log para verificar o role
        if (this.role == UserRoles.ROLE_STORE_ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_SUPERVISOR"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else if (this.role == UserRoles.ROLE_ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return login;
    }
}
