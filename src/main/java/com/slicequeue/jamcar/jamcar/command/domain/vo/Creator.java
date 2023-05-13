package com.slicequeue.jamcar.jamcar.command.domain.vo;

import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import lombok.Builder;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
public class Creator {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "uid", column = @Column(name = "creator_uid", nullable = false))
    )
    private final UserUid userUid;

    @NotNull
    @Comment("Jamcar 만든 사용자 이름")
    @Column(name = "creator_name")
    private final String name; //! 주의! - 사용자명 변경시 갱신 처리

    @Builder
    public Creator(UserUid userUid, String name) {
        this.userUid = userUid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Creator{" +
                "userUid=" + userUid +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creator creator)) return false;
        return Objects.equals(getUserUid(), creator.getUserUid()) && Objects.equals(getName(), creator.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserUid(), getName());
    }

    public UserUid getUserUid() {
        return userUid;
    }

    public String getName() {
        return name;
    }


}
