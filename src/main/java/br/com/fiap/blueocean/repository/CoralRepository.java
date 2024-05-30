package br.com.fiap.blueocean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.blueocean.model.Coral;

public interface CoralRepository extends JpaRepository<Coral, Long>{
    
}
