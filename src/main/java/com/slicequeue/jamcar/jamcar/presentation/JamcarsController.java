package com.slicequeue.jamcar.jamcar.presentation;

import com.slicequeue.jamcar.jamcar.command.application.CreateJamcarRequest;
import com.slicequeue.jamcar.jamcar.command.application.JamcarService;
import com.slicequeue.jamcar.user.command.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jamcars")
@RequiredArgsConstructor
public class JamcarsController {

    private final JamcarService jamcarService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<JamcarDto> createJamcar(
            @AuthenticationPrincipal User user,
            @RequestBody CreateJamcarRequest createJamcarRequest
    ) {
        return ResponseEntity.ok(jamcarService.createJamcar(user, createJamcarRequest));
    }

}
