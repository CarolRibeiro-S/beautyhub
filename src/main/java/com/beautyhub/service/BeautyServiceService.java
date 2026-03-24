package com.beautyhub.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.beautyhub.entity.BeautyService;
import com.beautyhub.repository.BeautyServiceRepository;

@Component
public class BeautyServiceService {

    private final BeautyServiceRepository serviceRepository;

    public BeautyServiceService(BeautyServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<BeautyService> findAll() {
        return serviceRepository.findAll();
    }

    public BeautyService findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
    }

    public BeautyService create(BeautyService service) {
        return serviceRepository.save(service);
    }

    public BeautyService update(Long id, BeautyService updated) {
        BeautyService current = findById(id);

        current.setNome(updated.getNome());
        current.setPreco(updated.getPreco());
        current.setDuracaoMinutos(updated.getDuracaoMinutos());

        return serviceRepository.save(current);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}

