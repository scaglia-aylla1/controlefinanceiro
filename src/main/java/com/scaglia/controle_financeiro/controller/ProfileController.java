package com.scaglia.controle_financeiro.controller;

import com.scaglia.controle_financeiro.dto.AuthDTO;
import com.scaglia.controle_financeiro.dto.ProfileDto;
import com.scaglia.controle_financeiro.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    @Operation(summary = "Registra um usuário")
    public ResponseEntity<ProfileDto> registerProfile(@RequestBody ProfileDto profileDto){
        ProfileDto registeredProfile = profileService.registerProfile(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }
    @GetMapping("/activate")
    @Operation(summary = "Ativa o perfil")
    public ResponseEntity<String> activeProfile(@RequestParam String token){
        boolean isActivated = profileService.activateProfile(token);
        if (isActivated){
            return ResponseEntity.ok("Perfil ativado com sucesso!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token de ativação não encontrado ou já usado");
        }
    }
    @PostMapping("/login")
    @Operation(summary = "Faz o login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDTO){
        try {
            if (!profileService.isAccountActive(authDTO.getEmail())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body( Map.of(
                        "message", "Conta não está ativa. Ative sua conta primeiro"
                ));
            }
            Map<String, Object> response = profileService.authenticationAndGenerateToken(authDTO);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }

}
