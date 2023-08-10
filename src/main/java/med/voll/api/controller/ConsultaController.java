package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var consulta = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {

        var agora = LocalDateTime.now();
        var page = repository.findAllByMotivoCancelamentoIsNullAndDataGreaterThanEqual(agora, paginacao).map(DadosDetalhamentoConsulta::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/canceladas")
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listarCanceladas(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {

        var page = repository.findAllByMotivoCancelamentoIsNotNull(paginacao).map(DadosDetalhamentoConsulta::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/realizadas")
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listarRealizadas(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {

        var agora = LocalDateTime.now();
        var page = repository.findAllByMotivoCancelamentoIsNullAndDataLessThanEqual(agora, paginacao).map(DadosDetalhamentoConsulta::new);
        return ResponseEntity.ok(page);
    }

}
