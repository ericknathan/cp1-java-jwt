package com.github.ericknathan.tasks.controllers;


import com.github.ericknathan.tasks.dtos.status.GetStatusListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/public")
@Tag(name = "Público", description = "Operações públicas, ou seja, que não necessitam de autenticação.")
public class PublicController {

    @GetMapping("status")
    @Operation(summary = "Listar status das tarefas", description = "Lista os status das tarefas disponíveis.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status listados com sucesso.", content = @Content(schema = @Schema(implementation = GetStatusListDTO.class), mediaType = "application/json"))
    })
    public ResponseEntity<GetStatusListDTO> getStatusList(UriComponentsBuilder builder){
          return ResponseEntity.ok(new GetStatusListDTO());
    }
}
