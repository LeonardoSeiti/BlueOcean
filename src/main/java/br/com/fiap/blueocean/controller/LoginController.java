package br.com.fiap.blueocean.controller;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import br.com.fiap.blueocean.model.Login;
import br.com.fiap.blueocean.repository.LoginRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "API de login do usuário")
public class LoginController {
    
    @Autowired
    LoginRepository repository;

    @Autowired
    PagedResourcesAssembler<Login> pageAssembler;
    
    // Metodo para listar todos os logins
    @GetMapping("/{all}")
    @Operation(summary = "Lista todos os logins cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de logins")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ResponseStatus(OK)
    public List<Login> findAll(@PathVariable String all) {
        return repository.findAll();
    }

    //Metodo para listar os logins com paginação
    @Operation(summary = "Lista os logins com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de logins")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @GetMapping
    public PagedModel<EntityModel<Login>> index(
        @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String email
        ){
            Page<Login> page =null;
            if(page == null){
                page = repository.findAll(pageable);
        }
        return pageAssembler.toModel(page);
    }
    //Metodo para salvar o login
    @Operation(summary = "Cria o login")
    @ApiResponse(responseCode = "200", description = "Login criado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @PostMapping
    @ResponseStatus(CREATED)
    public Login salvar(@RequestBody Login login) {
        return repository.save(login);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga o login")
    @ApiResponse(responseCode = "200", description = "Login apagado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação, insira o id correto")
    public void apagar(@PathVariable Long id) {
        validarLogin(id);
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza o login")
    @ApiResponse(responseCode = "200", description = "Login atualizado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    
    //Metodo para atualizar o login usando o id
    public Login atualizar(@PathVariable Long id,@RequestBody Login login) {
        validarLogin(id);
        login.setId(id);
        return repository.save(login);
    }    
    private void validarLogin(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Login não encontrado"));
    }
}
