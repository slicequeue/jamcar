package com.slicequeue.jamcar.user.command.domain;

import com.slicequeue.jamcar.common.base.BaseTimeEntity;
import com.slicequeue.jamcar.common.utils.StringPatternMatchingUtil;
import com.slicequeue.jamcar.user.command.domain.vo.Email;
import com.slicequeue.jamcar.user.command.domain.vo.Password;
import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseTimeEntity implements UserDetails {

    @EmbeddedId
    private UserUid userUid;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @NotNull
    @Comment("사용자 이름")
    @Column(length = 32)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public User() {
    }

    @Builder(builderMethodName = "newUser")
    private User(Email email, Password password, String name) {
        assert email != null;
        assert password != null;
        assert name != null;

        this.userUid = new UserUid();
        this.email = email;
        this.password = password;
        this.name = checkName(name);
    }

    private String checkName(String name) {
        if (name == null || !StringPatternMatchingUtil.isValidName(name))
            throw new IllegalArgumentException("name is not valid");
        return name;
    }

    public boolean checkPassword(Password password) {
        return this.password.equals(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email.toString();
    }

    public String getPassword() {
        return password.toString();
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
