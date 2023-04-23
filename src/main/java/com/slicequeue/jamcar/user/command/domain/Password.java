package com.slicequeue.jamcar.user.command.domain;

import com.slicequeue.jamcar.common.utils.StringPatternMatchingUtil;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Password {

    @Comment("비밀번호")
    @Column(length = 256, nullable = false)
    private String password;

    public Password(String password) {
        this.password = checkPassword(password);
    }
    private Password() {}

    @Override
    public String toString() {
        return password;
    }

    private String checkPassword(String password) {
        if (!StringPatternMatchingUtil.isValidPassword(password))
            throw new IllegalArgumentException("password validation fail.");
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
