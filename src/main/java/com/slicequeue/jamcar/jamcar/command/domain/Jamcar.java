package com.slicequeue.jamcar.jamcar.command.domain;

import com.slicequeue.jamcar.common.base.BaseTimeEntity;
import com.slicequeue.jamcar.common.base.BaseTimeSoftDeleteEntity;
import com.slicequeue.jamcar.common.type.Status;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Address;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Creator;
import com.slicequeue.jamcar.jamcar.command.domain.vo.JamcarId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "jamcar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Jamcar extends BaseTimeSoftDeleteEntity {

    @EmbeddedId
    private JamcarId id;

    @Embedded
    private Creator creator;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'TO_DO'")
    @Column(length = 32, nullable = false)
    Status status;

    @NotNull
    @Column
    private Instant startDate;

    @NotNull
    @Column
    private Instant endDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "postalCode", column = @Column(name = "from_postal_code", length = 8)),
            @AttributeOverride(name = "address", column = @Column(name = "from_address", length = 1024, nullable = false))
    })
    private Address fromAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "postalCode", column = @Column(name = "to_postal_code", length = 8)),
            @AttributeOverride(name = "address", column = @Column(name = "to_address", length = 1024, nullable = false))
    })
    private Address toAddress;

}
