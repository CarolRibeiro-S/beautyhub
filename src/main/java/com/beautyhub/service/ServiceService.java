package com.beautyhub.service;

import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.beautyhub.entity.BeautyService;
import com.beautyhub.repository.BeautyServiceRepository;

@Service
public class ServiceService {

    private final BeautyServiceRepository serviceRepository;

    public ServiceService(BeautyServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<BeautyService> findAll() {
        return serviceRepository.findAll();
    }

    public BeautyService findById(@NonNull Long id) {
        Objects.requireNonNull(id, "id não pode ser nulo");
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
    }

    public BeautyService create(@NonNull BeautyService service) {
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

