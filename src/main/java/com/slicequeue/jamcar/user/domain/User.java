package com.slicequeue.jamcar.user.domain;

import com.slicequeue.jamcar.common.base.BaseTimeEntity;
import lombok.AccessLevel;
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



}
