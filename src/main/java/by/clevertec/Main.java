package by.clevertec;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.entity.Category;
import by.clevertec.entity.Client;
import by.clevertec.entity.Review;
import by.clevertec.service.CarService;
import by.clevertec.service.CarShowroomService;
import by.clevertec.service.CategoryService;
import by.clevertec.service.ClientService;
import by.clevertec.service.ReviewService;
import by.clevertec.util.HibernateUtil;
import by.clevertec.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        TestDataImporter.importData();
        mainTest();
    }

    private static void mainTest() {

        CarService carService = new CarService();
        ClientService clientService = new ClientService();
        CarShowroomService showroomService = new CarShowroomService();
        CategoryService categoryService = new CategoryService();
        ReviewService reviewService = new ReviewService();

//             === CRUD для всех сущностей ===
//             Создание автосалонов
        CarShowroom showroom1 = CarShowroom.builder()
                .name("Elite Motors")
                .address("123 Main Street")
                .build();
        showroomService.addShowroom(showroom1);

        CarShowroom showroom2 = CarShowroom.builder()
                .name("Luxury Cars")
                .address("456 Elm Street")
                .build();
        showroomService.addShowroom(showroom2);

//         Создание категории

        Category categorySUV = Category.builder()
                .name("Suv")
                .build();
        categoryService.addCategory(categorySUV);

//        Извлечение категории по имени

        Category sedan = categoryService.getByName("Sedan");

        // Добавление автомобилей
        Car car1 = Car.builder()
                .model("Camry")
                .brand("Toyota")
                .yearsOfRelease(2022)
                .price(30000.0)
                .category(sedan)
                .showroom(showroom1)
                .build();
        Car car2 = Car.builder()
                .model("Accord")
                .brand("Honda")
                .yearsOfRelease(2021)
                .price(28000.0)
                .category(sedan)
                .showroom(showroom1)
                .build();
        Car car3 = Car.builder()
                .model("RAV4")
                .brand("Toyota")
                .yearsOfRelease(2023)
                .price(35000.0)
                .category(categorySUV)
                .showroom(showroom2)
                .build();
        Car car4 = Car.builder()
                .model("CR-V")
                .brand("Honda")
                .yearsOfRelease(2022)
                .price(34000.0)
                .category(categorySUV)
                .showroom(showroom2)
                .build();

        carService.addCar(car1);
        carService.addCar(car2);
        carService.addCar(car3);
        carService.addCar(car4);

        // Обновление автомобиля
        car1.setPrice(31000.0);
        carService.updateCar(car1);

        // Удаление автомобиля
        carService.deleteCar(car2);

//          Привязка автомобиля к автосалону
        Car car5 = Car.builder()
                .model("Civic")
                .brand("Honda")
                .yearsOfRelease(2024)
                .price(29000.0)
                .category(sedan)
                .showroom(showroom2)
                .build();
        carService.addCar(car5);

//          Регистрация клиентов

//          Получение клиента и авто по ID и покупка авто
        getClientAndAddById(clientService, carService);

//          Привязка автомобиля к клиенту
        Client client1 = Client.builder()
                .name("Alice Johnson")
                .dataOfRegistration(LocalDate.now())
                .build();
        clientService.addClient(client1);
        clientService.buyCar(client1, car1);

        Client client2 = Client.builder()
                .name("Bob Smith")
                .dataOfRegistration(LocalDate.now())
                .build();
        clientService.addClient(client2);
        clientService.buyCar(client2, car3);

//          Добавление отзывов
        reviewService.addReview(client1, car1, "Great performance and comfort!", 5);
        reviewService.addReview(client2, car3, "Excellent SUV with plenty of space.", 4);

//              Поиск автомобилей
//             Поиск по параметрам
        checkFindByParametrs(carService);

//         Сортировка по цене
        checkSortByPrice(carService);

//         Пагинация
        checkPagination(carService);

//         Полнотекстовый поиск отзывов
        checkSearchByKeyword(reviewService);

//         Поиск отзывов по рейтингу
        checkSearchByRating(reviewService);

        HibernateUtil.shutdown();
    }

    private static void getClientAndAddById(ClientService clientService, CarService carService) {

        System.out.println("\n###### Покупка авто по ID клиента и авто ######");

        Client testClient = clientService.getById(1L);
        Car testCar = carService.getCarById(1L);

        Client foundedClient = clientService.buyCar(testClient, testCar);
        System.out.println(foundedClient.getName() +" buy car : " + foundedClient.getCars());
    }

    private static void checkFindByParametrs(CarService carService) {

        System.out.println("\n###### Автомобили Toyota ######");

        List<Car> toyotaCars = carService.findCarsByFilters("Toyota", null, null, 0.0, null);
        toyotaCars.forEach(System.out::println);
    }

    private static void checkSortByPrice(CarService carService) {

        System.out.println("\n###### Автомобили, отсортированные по цене (убывание) ######");

        List<Car> sortedCars = carService.findCarsSortedByPrice(false);
        sortedCars.forEach(System.out::println);
    }

    private static void checkPagination(CarService carService) {

        System.out.println("\n###### Пагинация автомобилей (страница 1, размер 8) ######");

        List<Car> paginatedCars = carService.findCarsWithPagination(2, 8);
        paginatedCars.forEach(System.out::println);
    }

    private static void checkSearchByKeyword(ReviewService reviewService) {

        System.out.println("\n###### Поиск отзывов с ключевым словом 'SUV' ######");

        List<Review> suvReviews = reviewService.searchReviews("SUV");
        suvReviews.forEach(review -> {
            Hibernate.initialize(review.getClient());
            Hibernate.initialize(review.getCar());
        });
        suvReviews.forEach(System.out::println);
    }

    private static void checkSearchByRating(ReviewService reviewService) {

        System.out.println("\n###### Поиск отзывов с рейтингом 5 ######");

        List<Review> reviewsByRating = reviewService.searchReviewsByRating(5);
        reviewsByRating.forEach(System.out::println);
    }
}
