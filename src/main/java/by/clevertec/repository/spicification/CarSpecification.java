package by.clevertec.repository.spicification;

import by.clevertec.entity.Car;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    private CarSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Car> create(String brand, Integer year, String category, Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            Predicate predicates = cb.conjunction();

            if (category != null) {
                predicates = cb.and(predicates, cb.equal(root.get("category"), category));
            }
            if (brand != null) {
                predicates = cb.and(predicates, cb.equal(root.get("brand"), brand));
            }
            if (minPrice != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (year != null) {
                predicates = cb.and(predicates, cb.equal(root.get("year"), year));
            }

            return predicates;
        };
    }
}
