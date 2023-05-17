package com.sparta.amenityclonecoding.controller;


import com.sparta.amenityclonecoding.dto.UserRequestDto;
import com.sparta.amenityclonecoding.dto.ResponseDto;
import com.sparta.amenityclonecoding.dto.UserloginDto;
import com.sparta.amenityclonecoding.exception.Message;
import com.sparta.amenityclonecoding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid @RequestBody UserRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            return new ResponseDto(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody UserloginDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }

    @GetMapping("/logout/{userEmail}")
    public ResponseEntity<Message> logout(@PathVariable String userEmail) {
       return userService.logout(userEmail);
    }


}
