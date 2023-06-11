package com.slicequeue.jamcar.jamcar.command.application;

import com.slicequeue.jamcar.common.base.BaseRuntimeException;
import com.slicequeue.jamcar.common.exception.BadRequestException;
import com.slicequeue.jamcar.jamcar.command.domain.CreateJamcarService;
import com.slicequeue.jamcar.jamcar.command.domain.Jamcar;
import com.slicequeue.jamcar.jamcar.presentation.JamcarDto;
import com.slicequeue.jamcar.user.command.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JamcarService {

    private final CreateJamcarService createJamcarService;

    public JamcarDto createJamcar(User user, CreateJamcarRequest createJamcarRequest) {
        try {
            // jamcar 등록 처리
            Jamcar jamcar = Jamcar.create(createJamcarService, user.getUserUid(), user.getName(), createJamcarRequest);

            // DTO 반환 및 응답
            return JamcarDto.builder()
                    .id(jamcar.getId())
                    .fromPostalCode(jamcar.getFromAddress().getPostalCode())
                    .fromAddress(jamcar.getFromAddress().getAddress())
                    .toPostalCode(jamcar.getToAddress().getPostalCode())
                    .toAddress(jamcar.getToAddress().getAddress())
                    .creatorName(jamcar.getCreator().getName())
                    .startDate(jamcar.getStartDate())
                    .endDate(jamcar.getEndDate())
                    .status(jamcar.getStatus())
                    .build();
        } catch (IllegalArgumentException ie) {
            throw new BadRequestException(ie);

        } catch (IllegalStateException ie) {
            throw new BadRequestException(ie);
        }

    }

}
