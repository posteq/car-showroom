package by.clevertec.controller;

import by.clevertec.dto.ClientDto;
import by.clevertec.dto.ReviewDto;
import by.clevertec.dto.ReviewWithoutCarDto;
import by.clevertec.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto clientDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.create(clientDto));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> findClientById(@PathVariable("clientId") @Valid Long clientId) {
        return ResponseEntity.ok(clientService.findById(clientId));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> findAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable @Valid Long id,
                                                  @RequestBody @Valid ClientDto clientDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.update(id, clientDto));
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientById(@PathVariable("clientId") @Valid Long clientId) {
        clientService.delete(clientId);
    }

    @PostMapping("/{clientId}/buy-car/{carId}")
    public ResponseEntity<Void> buyCar(@PathVariable("clientId") @Valid Long clientId,
                                       @PathVariable("carId") @Valid Long carId) {
        clientService.buyCar(clientId,carId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clientId}/create-review/{carId}")
    public ResponseEntity<ReviewDto> leaveReview(@PathVariable @Valid Long clientId,
                                                 @PathVariable @Valid Long carId,
                                                 @RequestBody @Valid ReviewWithoutCarDto newReviewDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createReviewForCar(clientId, carId, newReviewDto));
    }
}
