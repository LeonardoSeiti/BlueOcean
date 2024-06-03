package br.com.fiap.blueocean.model;

import org.springframework.hateoas.EntityModel;
import br.com.fiap.blueocean.controller.CoralController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "t_coral")
public class Coral extends EntityModel<Coral>{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Tipos de corais são: 
    //Soft, LPS - coral duro com pólipos grandes e SPS - coral duro com pólipos pequenos
    @NotBlank(message = "{coral.tipo.notblank}")
    @Size(min = 3, max = 4, message = "{coral.tipo.size}")
    private String tipo;
    @NotBlank(message = "{coral.cor.notblank}")
    private String cor;

    public EntityModel<Coral> toEntityModel() {
        return EntityModel.of(
        this,

        linkTo(methodOn(CoralController.class).mostra(id)).withSelfRel(),
        linkTo(methodOn(CoralController.class).apagar(id)).withRel("apagar"),
        linkTo(methodOn(CoralController.class).atualizar(id, this)).withRel("atualizar"),
        linkTo(methodOn(CoralController.class).index(null, null, null)).withRel("corais"));
    }
}
