package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Page<Consulta> findAllByMotivoCancelamentoIsNullAndDataGreaterThanEqual(LocalDateTime data, Pageable paginacao);
    
    Page<Consulta> findAllByMotivoCancelamentoIsNullAndDataLessThanEqual(LocalDateTime data, Pageable paginacao);
    
    Page<Consulta> findAllByMotivoCancelamentoIsNotNull(Pageable paginacao);

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);
}
