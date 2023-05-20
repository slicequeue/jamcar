package com.slicequeue.jamcar.jamcar.presentation;

import com.slicequeue.jamcar.jamcar.command.application.CreateJamcarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jamcars")
@RequiredArgsConstructor
public class JamcarsController {

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<Object> createJamcar(@RequestBody CreateJamcarRequest createJamcarRequest) {
        // TODO 구현
        return ResponseEntity.ok(new Object());
    }

}
