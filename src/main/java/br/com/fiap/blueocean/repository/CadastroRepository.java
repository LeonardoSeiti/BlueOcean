package br.com.fiap.blueocean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.blueocean.model.Cadastro;

public interface CadastroRepository extends JpaRepository<Cadastro, Long>{
    
}
