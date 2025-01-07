package by.clevertec.service;

import by.clevertec.dto.ClientDto;
import by.clevertec.dto.ReviewDto;
import by.clevertec.dto.ReviewWithoutCarDto;
import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.entity.Review;
import by.clevertec.exception.CarNotFoundException;
import by.clevertec.exception.ClientNotFoundException;
import by.clevertec.mapper.ClientMapper;
import by.clevertec.mapper.ReviewMapper;
import by.clevertec.repository.CarRepository;
import by.clevertec.repository.ClientRepository;
import by.clevertec.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final CarRepository carRepository ;
    private final ClientMapper clientMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ClientDto create(ClientDto clientDto) {
        Client client = clientRepository.save(clientMapper.toClient(clientDto));
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public ClientDto update(Long id,ClientDto clientDto) {
        return clientMapper.toClientDto(
                clientRepository.findById(id)
                        .map(client -> {
                            Client updatedClientDTO = clientMapper.toClient(clientDto);
                            client.setName(updatedClientDTO.getName());
                            client.setContacts(updatedClientDTO.getContacts());
                            client.setDataRegistration(updatedClientDTO.getDataRegistration());
                            return clientRepository.save(client);
                        })
                        .orElseThrow(() -> new ClientNotFoundException(id))
        );
    }

    @Transactional
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public ClientDto findById(Long id) {
        return clientMapper.toClientDto(clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id))
        );
    }

    @Transactional
    public List<ClientDto> findAll() {
        return clientMapper.toClientDtoList(clientRepository.findAll());
    }

    @Transactional
    public void buyCar(Long clientId, Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        client.getCars().add(car);
        clientRepository.save(client);
    }

    @Transactional
    public ReviewDto createReviewForCar(Long clientId, Long carId, ReviewWithoutCarDto reviewDto) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        Review review = new Review();
        review.setText(reviewDto.text());
        review.setRating(reviewDto.rating());
        review.setClient(client);
        review.setCar(car);

        return reviewMapper.toReviewDto(reviewRepository.save(review));
    }

}
