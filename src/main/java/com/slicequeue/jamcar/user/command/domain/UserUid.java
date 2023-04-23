package com.slicequeue.jamcar.user.command.domain;

import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserUid implements Serializable {

    @Comment("사용자 식별값")
    @Column(columnDefinition = "BINARY(16)", updatable = false)
    private final UUID uid;

    public UserUid(UUID uid) {
        this.uid = uid;
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
}
