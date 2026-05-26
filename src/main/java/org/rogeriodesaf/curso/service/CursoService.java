package org.rogeriodesaf.curso.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.rogeriodesaf.curso.dto.CursoRequestDTO;
import org.rogeriodesaf.curso.dto.CursoResponseDTO;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.curso.exception.CursoJaCadastradoException;
import org.rogeriodesaf.curso.exception.CursoNaoEncontradoException;
import org.rogeriodesaf.curso.mapper.CursoMapper;
import org.rogeriodesaf.curso.repository.CursoRepository;

import java.util.List;

@ApplicationScoped
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    @Transactional
    public  CursoResponseDTO cadastrar(CursoRequestDTO cursoRequestDTO){
        Curso curso = cursoRepository.findByTitulo(cursoRequestDTO.titulo());
        if (curso != null){
            throw new CursoJaCadastradoException("Curso já cadastrado");
        }
          curso = cursoMapper.toEntity(cursoRequestDTO);
         curso.ativo = true;
         cursoRepository.persist(curso);
        return cursoMapper.toResponse(curso);
    }

    public CursoResponseDTO buscarPorId(Long id){
        Curso curso = cursoRepository.findById(id);
        if (curso == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }
        return cursoMapper.toResponse(curso);
    }

    public List<CursoResponseDTO> listarTodos(){
        List<Curso> cursos = cursoRepository.findByAtivo();
        if (cursos == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }
        return cursos.stream()
                .map(cursoMapper::toResponse)
                .toList();
    }

    @Transactional
    public void desativarCurso(Long id){
        Curso curso = cursoRepository.findById(id);
        if (curso == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }
        curso.ativo = false;
        cursoRepository.persist(curso);
    }

    @Transactional
    public CursoResponseDTO ativarCurso(Long id){
        Curso curso = cursoRepository.findById(id);
        if (curso == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }
        curso.ativo = true;
        cursoRepository.persist(curso);
        return cursoMapper.toResponse(curso);
    }

    @Transactional
    public CursoResponseDTO atualizarCurso(Long id, CursoRequestDTO cursoRequestDTO){
        Curso curso = cursoRepository.findById(id);
        if (curso == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }
        curso.titulo = cursoRequestDTO.titulo();
        curso.descricao = cursoRequestDTO.descricao();
        cursoRepository.persist(curso);
        return cursoMapper.toResponse(curso);
    }

}
