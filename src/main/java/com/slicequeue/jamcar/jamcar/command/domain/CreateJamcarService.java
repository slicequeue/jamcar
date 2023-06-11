package com.slicequeue.jamcar.jamcar.command.domain;

import com.slicequeue.jamcar.common.type.Status;
import com.slicequeue.jamcar.jamcar.command.application.CreateJamcarRequest;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Address;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Creator;
import com.slicequeue.jamcar.user.command.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateJamcarService {

    private final JamcarRepository jamcarRepository;

    /**
     * 잼카를 생성합니다.
     * @param request* CreateJamcarRequest 객체
     * @param creator* 작성자 VO 객체
     * @return 생성한 Jamcar 도메인 모델 객체
     */
    public Jamcar create(CreateJamcarRequest request, Creator creator) {
        Assert.notNull(request, CreateJamcarRequest.class.getSimpleName());
        Assert.notNull(creator, User.class.getSimpleName());
        Address fromAddress = Address.newAddress()
                .postalCode(request.getFromPostalCode())
                .address(request.getFromAddress())
                .build();
        Address toAddress = Address.newAddress()
                .postalCode(request.getToPostalCode())
                .address(request.getToAddress())
                .build();
        /*
         * 생성 정책: 같은 사용자 내 출발지/도착지 같고 진행중/대기인 상태인 경우가 존재하지 않아야 생성할 수 있다.
         */
        Optional<Jamcar> optional = jamcarRepository
                .findByCreatorAndFromAddressAndToAddressAndStatusInAndIsDeletedIsFalse(creator, fromAddress, toAddress,
                        List.of(Status.TO_DO, Status.ON_PROGRESS));

        if (optional.isPresent()) {
            throw new IllegalStateException("생성 정책 위반: 같은 사용자 내 출발지/도착지 같고 진행중/대기인 상태인 경우가 존재하지 않아야 생성할 수 있다.");
        }

        Instant now = Instant.now();

        Jamcar jamcar = Jamcar.newJamcar()
                .fromAddress(fromAddress)
                .toAddress(toAddress)
                .startDate(now)
                .endDate(now.plus(1, ChronoUnit.WEEKS))
                .build();

        return jamcarRepository.save(jamcar);

    }

}
