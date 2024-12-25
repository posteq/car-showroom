package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.repository.ClientRepository;

import java.util.List;

public class ClientService {

    private final ClientRepository clientRepository = new ClientRepository();

    public void addClient(Client client) {
        clientRepository.create(client);
    }

    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client.getId());
    }

    public Client getById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    public Client buyCar(Client client, Car car) {
        return clientRepository.buyCar(client, car);
    }

}
