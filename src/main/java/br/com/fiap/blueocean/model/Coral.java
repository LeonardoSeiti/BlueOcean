package br.com.fiap.blueocean.model;

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
@Table(name = "T_CORAL")
public class Coral {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //Tipos de corais são: 
    //Soft, LPS - coral duro com pólipos grandes e SPS - coral duro com pólipos pequenos
    @NotBlank(message = "{coral.tipo.notblank}")
    @Size(min = 3, max = 4, message = "{coral.tipo.size}")
    private String tipo;
    @NotBlank(message = "{coral.cor.notblank}")
    private String cor;
}
