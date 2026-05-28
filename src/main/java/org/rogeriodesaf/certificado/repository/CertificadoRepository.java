package org.rogeriodesaf.certificado.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.certificado.entity.Certificado;

@ApplicationScoped
public class CertificadoRepository implements PanacheRepository<Certificado> {

    public Certificado buscarPorUsuarioECurso(Long usarioId, Long cursoId){
        return find("usuario.id = ?1 and curso.id = ?2", usarioId, cursoId).firstResult();
    }
}
