package br.com.fiap.blueocean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.blueocean.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long>{
    
}
