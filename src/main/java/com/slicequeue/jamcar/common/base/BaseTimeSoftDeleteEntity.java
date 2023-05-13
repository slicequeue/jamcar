package com.slicequeue.jamcar.common.base;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseTimeSoftDeleteEntity extends BaseTimeEntity {

    @Comment("기록 삭제 여부")
    @Column(name = "is_deleted")
    @ColumnDefault("false")
    protected Boolean isDeleted = false;

    @Comment("기록 삭제 시간")
    @Column(name = "deleted_at")
    protected Instant deletedAt;

    public void changeDeletion(Boolean isDeleted, Instant deletedAt) {
        if (Boolean.TRUE.equals(isDeleted) && Objects.isNull(deletedAt))
            throw new IllegalArgumentException("isDeleted is true, deletedAt must not be null.");

        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

}
