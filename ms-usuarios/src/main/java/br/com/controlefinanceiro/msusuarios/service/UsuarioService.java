package br.com.controlefinanceiro.msusuarios.service;

import br.com.controlefinanceiro.msusuarios.dto.UsuarioRequestDTO;
import br.com.controlefinanceiro.msusuarios.dto.UsuarioResponseDTO;
import br.com.controlefinanceiro.msusuarios.entity.Usuario;
import br.com.controlefinanceiro.msusuarios.exception.EmailJaCadastradoException;
import br.com.controlefinanceiro.msusuarios.exception.UsuarioNaoEncontradoException;
import br.com.controlefinanceiro.msusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("E-mail já está em uso: " + dto.email());
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .build();

        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {
        return toResponse(usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + id)));
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAllByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponseDTO atualizar(UUID id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + id));

        if (!usuario.getEmail().equals(dto.email()) &&
                usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("E-mail já está em uso: " + dto.email());
        }

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void desativar(UUID id) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + id));

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getAtivo(),
                usuario.getDataCriacao()
        );
    }
}
