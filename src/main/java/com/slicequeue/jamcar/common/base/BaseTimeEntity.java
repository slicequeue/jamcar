package com.slicequeue.jamcar.common.base;

import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseTimeEntity {

    @CreationTimestamp
    @Comment("생성일시")
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Comment("수정일시")
    @Column
    private Instant updatedAt;

}
