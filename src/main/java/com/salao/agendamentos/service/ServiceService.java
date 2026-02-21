package com.salao.agendamentos.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.salao.agendamentos.repository.ServiceRepository;

@Component
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<com.salao.agendamentos.entity.Service> findAll() {
        return serviceRepository.findAll();
    }

    public com.salao.agendamentos.entity.Service findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
    }

    public com.salao.agendamentos.entity.Service create(com.salao.agendamentos.entity.Service service) {
        return serviceRepository.save(service);
    }

    public com.salao.agendamentos.entity.Service update(Long id, com.salao.agendamentos.entity.Service updated) {
        com.salao.agendamentos.entity.Service current = findById(id);

        // ✅ Ajuste estes setters conforme o seu Service.java
        // Exemplo:
        // current.setNome(updated.getNome());
        // current.setPreco(updated.getPreco());
        // current.setDuracaoMinutos(updated.getDuracaoMinutos());

        return serviceRepository.save(current);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}