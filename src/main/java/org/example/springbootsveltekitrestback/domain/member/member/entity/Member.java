package org.example.springbootsveltekitrestback.domain.member.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.*;
import org.example.springbootsveltekitrestback.standard.base.BaseTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Member extends BaseTime {
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String refreshToken;
    // 캐시 데이터
    @Transient
    private Boolean _isAdmin;

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStringList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Transient
    public List<String> getAuthoritiesAsStringList() {
        List<String> authorities = new ArrayList<>();

        authorities.add("ROLE_MEMBER");

        if (isAdmin()) {
            authorities.add("ROLE_ADMIN");
        }

        return authorities;
    }

    @Transient
    public String getName() {
        return username;
    }

    @Transient
    public boolean isAdmin() {
        if (this._isAdmin != null)
            return this._isAdmin;

        this._isAdmin = List.of("system", "admin").contains(getUsername());

        return this._isAdmin;
    }

    @Transient
    public void setAdmin(boolean admin) {
        this._isAdmin = admin;
    }
}
