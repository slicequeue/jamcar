package com.slicequeue.jamcar.user.command.domain.vo;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserUid implements Serializable {

    @Comment("사용자 식별값")
    @Type(type = "uuid-char")
    @Column(updatable = false)
    private final UUID uid;

    public UserUid(UUID uid) {
        this.uid = uid;
    }

    public UserUid(String uid) {
        this.uid = UUID.fromString(uid);
    }

    public UserUid() {
        this.uid = UUID.randomUUID();
    }

    public UUID getUid() {
        return uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserUid userUid = (UserUid) o;
        return Objects.equals(uid, userUid.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return getUid().toString();
    }
}
