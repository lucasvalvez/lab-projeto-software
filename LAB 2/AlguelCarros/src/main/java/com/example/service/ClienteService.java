package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cliente;
import com.example.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente save(Cliente cliente) {
        if (cliente.getId() == 0 && clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new RuntimeException("Email já cadastrado");
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(int id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public void deleteById(int id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}
