package br.com.controlefinanceiro.msusuarios.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        Boolean ativo,
        LocalDateTime criandoEm
) {}
