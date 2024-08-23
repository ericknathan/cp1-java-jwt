package com.github.ericknathan.tasks.controllers;


import com.github.ericknathan.tasks.dtos.status.GetStatusListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("status")
    public ResponseEntity<GetStatusListDTO> getStatusList(UriComponentsBuilder builder){
          return ResponseEntity.ok(new GetStatusListDTO());
    }
}
