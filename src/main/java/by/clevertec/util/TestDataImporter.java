package by.clevertec.util;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.entity.Category;
import by.clevertec.entity.Client;
import by.clevertec.repository.CarRepository;
import by.clevertec.repository.CarShowroomRepository;
import by.clevertec.repository.CategoryRepository;
import by.clevertec.repository.ClientRepository;
import by.clevertec.repository.ReviewRepository;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TestDataImporter {

    public void importData() {

        CarRepository carRepository = new CarRepository();
        CarShowroomRepository showroomRepository = new CarShowroomRepository();
        ClientRepository clientRepository = new ClientRepository();
        ReviewRepository reviewRepository = new ReviewRepository();
        CategoryRepository categoryRepository = new CategoryRepository();

        Category sedan = Category.builder()
                .name("Sedan")
                .build();
        categoryRepository.create(sedan);

        Category jeep = Category.builder()
                .name("Jeep")
                .build();
        categoryRepository.create(jeep);

        Category truck = Category.builder()
                .name("Truck")
                .build();
        categoryRepository.create(truck);

        CarShowroom showroom1 = CarShowroom.builder()
                .name("Prestige Cars")
                .address("45 Lenina")
                .build();
        showroomRepository.create(showroom1);

        CarShowroom showroom2 = CarShowroom.builder()
                .name("Regular Cars")
                .address("260 Kirova")
                .build();
        showroomRepository.create(showroom2);

        List<Car> cars = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Car car = Car.builder()
                    .brand(i % 2 == 0 ? "Geely" : "Audi")
                    .model("Model-" + i)
                    .yearsOfRelease(2000 + i % 3)
                    .price(20000D + i * 3000)
                    .showroom(i % 2 == 0 ? showroom1 : showroom2)
                    .build();

            if (i % 3 == 0) {
                car.setCategory(sedan);
            } else {if (i % 3 == 1) {
                    car.setCategory(jeep);
                } else {
                    car.setCategory(truck);
                }
            }

            carRepository.create(car);
            cars.add(car);
        }

        List<Client> clients = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Client client = Client.builder()
                    .name("Client-" + i)
                    .contacts(List.of("email" + i + "@example.com", "+1234567890"))
                    .dataOfRegistration(LocalDate.now().minusDays(i * 10L))
                    .build();
            clientRepository.create(client);
            clients.add(client);
        }

        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            Car car = cars.get(i);
            clientRepository.buyCar(client, car);
        }

        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            Client client = clients.get(i % clients.size());
            reviewRepository.addReview(client, car, "Great car! Review by " + client.getName(), 5);
        }

    }
}
