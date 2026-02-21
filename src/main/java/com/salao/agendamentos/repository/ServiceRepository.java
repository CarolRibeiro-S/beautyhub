package com.salao.agendamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salao.agendamentos.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {}
