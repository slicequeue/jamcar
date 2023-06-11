package com.slicequeue.jamcar.jamcar.command.domain;

import com.slicequeue.jamcar.common.base.BaseTimeSoftDeleteEntity;
import com.slicequeue.jamcar.common.type.Status;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Address;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Creator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "jamcar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Jamcar extends BaseTimeSoftDeleteEntity {

//    @EmbeddedId
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private JamcarId id;
//    @EmbeddedId - 사용하게 되면 @GeneratedValue IDENTITY 적용이 어려움이 있음

    @Id
    @Comment("jamcar 일련번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

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



    @Builder(builderMethodName = "newJamcar")
    public Jamcar(Creator creator, Instant startDate, Instant endDate, Address fromAddress, Address toAddress) {
        validCreator(creator);
        validStartEndDate(startDate, endDate);
        validFromToAddress(fromAddress, toAddress);

        this.creator = creator;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.status = Instant.now().isBefore(startDate)
                ? Status.TO_DO
                : Status.ON_PROGRESS;
    }

    private void validCreator(Creator creator) {
        Assert.notNull(creator, "creator");
    }

    private void validFromToAddress(Address fromAddress, Address toAddress) {
        Assert.notNull(fromAddress, "fromAddress must not be null.");
        Assert.notNull(toAddress, "toAddress must not be null.");
        if (fromAddress.equals(toAddress)) {
            throw new IllegalArgumentException("출발지와 도착지가 같을 수 없습니다.");
        }
    }

    private void validStartEndDate(Instant startDate, Instant endDate) {
        Assert.notNull(startDate, "startDate must not be null.");
        Assert.notNull(endDate, "endDate must not be null.");
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("시작일 끝 범위가 올바르지 않습니다.");
        }
    }
}
