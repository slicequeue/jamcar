package com.slicequeue.jamcar.jamcar.command.domain;

import com.slicequeue.jamcar.common.type.Status;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Address;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JamcarRepository extends JpaRepository<Jamcar, Long> {
    Optional<Jamcar> findByCreatorAndFromAddressAndToAddressAndStatusInAndIsDeletedIsFalse(
            Creator creator, Address fromAddress, Address toAddress, List<Status> status);
}
