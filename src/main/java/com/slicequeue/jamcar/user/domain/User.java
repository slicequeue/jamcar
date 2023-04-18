package com.slicequeue.jamcar.user.domain;

import com.slicequeue.jamcar.common.base.BaseTimeEntity;
import com.slicequeue.jamcar.common.utils.StringPatternMatchingUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

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

    @Builder
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
}
