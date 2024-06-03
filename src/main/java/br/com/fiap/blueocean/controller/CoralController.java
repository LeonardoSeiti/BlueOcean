package br.com.fiap.blueocean.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.com.fiap.blueocean.model.Coral;
import br.com.fiap.blueocean.repository.CoralRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("CadastrarCoral")
@Tag(name = "Coral", description = "API de cadastro de corais para análise de qualidade da água do mar")
public class CoralController {
    @Autowired
    CoralRepository repository;

    @Autowired
    PagedResourcesAssembler<Coral> pageAssembler;
    // Metodo para listar os corais por id
    @GetMapping("{id}")
    @Operation(summary = "Lista todos os corais cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de corais")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    public EntityModel<Coral> mostra(@PathVariable Long id) {
        var coral = repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(NOT_FOUND, "Coral não encontrado"));
        return coral.toEntityModel();
    }
    //Metodo para listar os corais com paginação
    @Operation(summary = "Lista os corais com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de corais")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @GetMapping
    public PagedModel<EntityModel<Coral>> index(
            @ParameterObject @PageableDefault(size=10, sort = "id", direction = Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String cor
            ){
                Page<Coral> page = null;
                if (page == null) {
                    page = repository.findAll(pageable);
            }
            return pageAssembler.toModel(page);
    }
    //Metodo para salvar um novo coral
    @Operation(summary = "Cadastra um novo coral")
    @ApiResponse(responseCode = "201", description = "Coral salvo com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @PostMapping
    public ResponseEntity<EntityModel<Coral>> salvar(@RequestBody @Valid Coral coral) {
        repository.save(coral);
        return ResponseEntity
        .created(coral.toEntityModel().getRequiredLink("self").toUri())
        .body(coral.toEntityModel());
    }
    //Metodo para apagar um coral
    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga um cadastro de coral")
    @ApiResponse(responseCode = "200", description = "Coral apagado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "404", description = "Coral não encontrado")
    public ResponseEntity<Coral> apagar (@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Coral não encontrado"));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
        
    }
    //Metodo para atualizar um coral
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um cadastro de coral")
    @ApiResponse(responseCode = "200", description = "Coral atualizado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "404", description = "Coral não encontrado")

    public ResponseEntity<Coral> atualizar(@PathVariable Long id, @RequestBody Coral coral) {
        validarCoral(id);
        coral.setId(id);
        repository.save(coral);
        return ResponseEntity.ok(coral);
    }
    //Metodo para validar o coral
    private void validarCoral(Long id){
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Coral não encontrado"));
    }
}
