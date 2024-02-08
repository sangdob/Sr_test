package com.SrBackend.controller;

import com.SrBackend.dto.SignUpDto;
import com.SrBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpDto signUpDto) {
        return new ResponseEntity(
            userService.signUp(signUpDto) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
