package com.beautyhub.repository;

import com.beautyhub.entity.BeautyService;
import com.beautyhub.entity.Salao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeautyServiceRepository extends JpaRepository<BeautyService, Long> {

    List<BeautyService> findBySalao(Salao salao);

    // para filtrar por preço, depois você pode adicionar:
    // List<Service> findByPrecoLessThanEqual(BigDecimal maxPrice);
}