package com.slicequeue.jamcar.user.command.domain;

import com.slicequeue.jamcar.common.utils.StringPatternMatchingUtil;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Email {

    @Comment("이메일")
    @Column(length = 128, nullable = false)
    private String email;

    public Email(String email) {
        this.email = checkEmail(email);
    }

    private Email() {}

    @Override
    public String toString() {
        return email;
    }

    private String checkEmail(String email) {
        if (!StringPatternMatchingUtil.isValidEmail(email))
            throw new IllegalArgumentException("email validation fail.");
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
