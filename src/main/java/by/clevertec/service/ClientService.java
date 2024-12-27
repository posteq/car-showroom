package by.clevertec.service;

import by.clevertec.dto.ClientDto;
import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.exception.ClientNotFoundException;
import by.clevertec.mapper.ClientMapper;
import by.clevertec.repository.CarRepository;
import by.clevertec.repository.CarShowroomRepository;
import by.clevertec.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.smartcardio.CardNotPresentException;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final CarRepository carRepository ;
    private final CarShowroomRepository carShowroomRepository;
    private final ClientMapper clientMapper;

    public ClientDto addClient(ClientDto clientDto) {
        Client client = clientRepository.save(clientMapper.toClient(clientDto));
        return clientMapper.toClientDto(client);
    }

    public ClientDto update(Long id,ClientDto clientDto) {
        return clientMapper.toClientDto(
                clientRepository.findById(id)
                        .map(client -> {
                            Client updatedClientDTO = clientMapper.toClient(clientDto);
                            client.setName(updatedClientDTO.getName());
                            client.setDataOfRegistration(updatedClientDTO.getDataOfRegistration());
                            return clientRepository.save(client);
                        })
                        .orElseThrow(() -> new ClientNotFoundException("Client not found with id : " + id))
        );
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public ClientDto getById(Long id) {
        return clientMapper.toClientDto(clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id : " + id))
        );
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public ClientDto buyCar(Long clientId, Long carId) {
        carRepository.findById(clientId).orElseThrow(()-> new CardNotPresentException(id))
    }

}
