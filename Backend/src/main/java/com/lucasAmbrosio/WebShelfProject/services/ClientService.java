package com.lucasAmbrosio.WebShelfProject.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasAmbrosio.WebShelfProject.DTOs.ClientDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.ClientWithLibName;
import com.lucasAmbrosio.WebShelfProject.DTOs.ClientWithLoansDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.LoanDTO;
import com.lucasAmbrosio.WebShelfProject.models.Client;
import com.lucasAmbrosio.WebShelfProject.repositories.ClientRepository;
import com.lucasAmbrosio.WebShelfProject.services.exceptions.NotFoundInDbException;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    //! Função para criar um cliente
    public Client create(Client client){
        client.setId(null);
        LocalDate date = LocalDate.now();

        client.setRegistryDate(date);

        client.setHasActiveLoans(false);
        clientRepository.save(client);

        return client;        
    }

    //! Função para achar um cliente
    public ClientWithLibName findById(String id){
        Client client = this.clientRepository.findById(id).orElseThrow();

        ClientWithLibName clientWithLibName = new ClientWithLibName(
            client.getId(), 
            client.getName(), 
            client.getCpf(), 
            client.getEmail(), 
            client.isHasActiveLoans(),
            client.getRegistryDate(), 
            client.getLibrary().getLibraryName()
        );

        return clientWithLibName;
    }

    //! Retorna o cliente com historico de empresitmos
    public ClientWithLoansDTO findClientWithLoans(String id){
        Client client = this.clientRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("dado nao encontrado"));

        List<LoanDTO> loans = client.getLoans().stream().map(loan -> {
            LoanDTO loanDTO = new LoanDTO(
                loan.getId(), 
                loan.getClient().getName(), 
                loan.getBook().getTitle(), 
                loan.getLoanDate(), 
                loan.getReturnDate(), 
                loan.isActive());

                return loanDTO;
        }).collect(Collectors.toList());


        ClientWithLoansDTO clientWithLoansDTO = new ClientWithLoansDTO(
            client.getId(), 
            client.getName(), 
            client.getCpf(), 
            client.isHasActiveLoans(),
            client.getRegistryDate(), 
            loans
        );

        return clientWithLoansDTO;
    }

    public List<ClientDTO> findClientByNameAndLibrary(String name, String libId){
        List<Client> rawClientList = this.clientRepository.findClientsByNameAndLibraryId(name, libId);

        List<ClientDTO> ClientList = rawClientList.stream().map(client -> {
            ClientDTO clientDTO = new ClientDTO(
                client.getId(),
                client.getCpf(),
                client.getName(),
                client.isHasActiveLoans(),
                client.getEmail(),
                client.getRegistryDate()
            );

            return clientDTO;
        }).collect(Collectors.toList());

        return ClientList;
    }

    public List<ClientDTO> findClientByCpfAndLibrary(String cpf, String libId){
        List<Client> rawClientList = this.clientRepository.findClientsByLibraryAndCpf(libId, cpf);

        List<ClientDTO> clientList = rawClientList.stream().map(client -> {
            ClientDTO clientDTO = new ClientDTO(
                client.getId(),
                client.getCpf(),
                client.getName(),
                client.isHasActiveLoans(),
                client.getEmail(),
                client.getRegistryDate()
            );

            return clientDTO;
        }).collect(Collectors.toList());

        return clientList;
    }
}
