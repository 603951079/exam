package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@ToString
@NoArgsConstructor
public class SysUser extends BaseEntity implements UserDetails {
    @Setter
    @Getter
    private String zhName;

    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    @Getter
    private String phone;

    @Setter
    @Getter
    private String flag;

    @Setter
    @Getter
    private String remark;

    @Transient
    @Setter
    private Collection<? extends GrantedAuthority> authorities;

    @Setter
    private boolean accountNonExpired;

    @Setter
    private boolean accountNonLocked;

    @Setter
    private boolean isCredentialsNonExpired;

    @Setter
    private boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.isEnabled;
    }

    public SysUser(String id, String zhName, String username, String phone) {
        super.setId(id);
        this.zhName = zhName;
        this.username = username;
        this.phone = phone;
    }
}
