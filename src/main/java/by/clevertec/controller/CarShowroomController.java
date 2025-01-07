package by.clevertec.controller;

import by.clevertec.dto.CarShowroomDto;
import by.clevertec.service.CarShowroomService;
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
@Validated
@RequestMapping("/api/showrooms")
@RequiredArgsConstructor
public class CarShowroomController {

    private final CarShowroomService carShowroomService;

    @PostMapping
    public ResponseEntity<CarShowroomDto> createCarShowroom(@RequestBody @Valid CarShowroomDto carShowroomDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carShowroomService.create(carShowroomDto));
    }

    @GetMapping("/{showroomId}")
    public ResponseEntity<CarShowroomDto> findCarShowroomById(@PathVariable("showroomId") @Valid Long showroomId) {
        return ResponseEntity.ok(carShowroomService.findById(showroomId));
    }

    @GetMapping
    public ResponseEntity<List<CarShowroomDto>> findAllCarShowrooms() {
        return ResponseEntity.ok(carShowroomService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarShowroomDto> updateCarShowroom(@PathVariable @Valid Long id,
                                                            @RequestBody @Valid CarShowroomDto carShowroomDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carShowroomService.update(id, carShowroomDto));
    }

    @DeleteMapping("/{showroomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarShowroomById(@PathVariable("showroomId") @Valid Long showroomId) {
        carShowroomService.delete(showroomId);
    }
}
