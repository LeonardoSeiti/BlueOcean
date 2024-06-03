package br.com.fiap.blueocean.controller;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.data.web.PagedResourcesAssembler;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import br.com.fiap.blueocean.model.Cadastro;
import br.com.fiap.blueocean.repository.CadastroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("cadastro")
@Tag(name = "Cadastro", description = "API de cadastro do usuário")
public class CadastroController {
    
    @Autowired
    CadastroRepository repository;

    @Autowired
    PagedResourcesAssembler<Cadastro> pageAssembler;
    
    // Metodo para listar os cadastros por id
    @GetMapping("{id}")
    @Operation(summary = "Lista todos os cadastros cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de cadastros")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public EntityModel<Cadastro> mostra(@PathVariable Long id) {
        var cadastro = repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(NOT_FOUND, "Cadastro não encontrado"));
        return cadastro.toEntityModel();
    }

    //Metodo para listar os cadastros com paginação
    @Operation(summary = "Lista com os cadastros com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de cadastros")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @GetMapping
    public PagedModel<EntityModel<Cadastro>> index(
        @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String email
        ){
            Page<Cadastro> page =null;
            if(page == null){
                page = repository.findAll(pageable);
        }
        return pageAssembler.toModel(page);
    }
    //Metodo para salvar o cadastro
    @Operation(summary = "Cria o cadastro")
    @ApiResponse(responseCode = "200", description = "cadastro criado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<EntityModel<Cadastro>> salvar(@RequestBody @Valid Cadastro cadastro) {
        repository.save(cadastro);
        
        return ResponseEntity
        .created(cadastro.toEntityModel().getRequiredLink("self").toUri())
        .body(cadastro.toEntityModel());
    }
    //Metodo para apagar o cadastro
    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga o cadastro")
    @ApiResponse(responseCode = "200", description = "Cadastro apagado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação, insira o id correto")
    public ResponseEntity<Cadastro> apagar(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cadastro não encontrado"));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza o cadastro")
    @ApiResponse(responseCode = "200", description = "Cadastro atualizado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    
    //Metodo para atualizar o cadastro usando o id
    public ResponseEntity<Cadastro> atualizar(@PathVariable Long id,@RequestBody Cadastro cadastro) {
        validarCadastro(id);
        cadastro.setId(id);
        repository.save(cadastro);
        return ResponseEntity.ok(cadastro);
    }

    //Metodo para validar o cadastro   
    private void validarCadastro(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Cadastro não encontrado"));
    }
}
