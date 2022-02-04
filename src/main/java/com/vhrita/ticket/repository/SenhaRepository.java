package com.vhrita.ticket.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vhrita.ticket.model.Senha;

@Repository
public interface SenhaRepository extends JpaRepository<Senha, Long> {
	@Query(value = "SELECT * FROM senha WHERE tipo = 'P' ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Senha findLastTicketP();
	
	@Query(value = "SELECT * FROM senha WHERE tipo = 'N' ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Senha findLastTicketN();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE senha SET atual=bool(true) WHERE id=?1", nativeQuery=true)
	void setCurrent(Long id);
}
