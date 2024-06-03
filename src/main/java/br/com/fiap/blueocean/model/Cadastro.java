package br.com.fiap.blueocean.model;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import br.com.fiap.blueocean.controller.CadastroController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cadastro extends EntityModel<Cadastro>{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;

    public EntityModel<Cadastro> toEntityModel() {
        return EntityModel.of(this,
        linkTo(methodOn(CadastroController.class).mostra(id)).withSelfRel(),
        linkTo(methodOn(CadastroController.class).apagar(id)).withRel("apagar"),
        linkTo(methodOn(CadastroController.class).atualizar(id, this)).withRel("atualizar"),
        linkTo(methodOn(CadastroController.class).index(null, null, null)).withRel("cadastro"));
    }
}
