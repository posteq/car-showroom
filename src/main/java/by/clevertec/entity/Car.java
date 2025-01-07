package by.clevertec.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "cars")
@ToString(exclude = {"category", "review"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedEntityGraph(
        name = "withCategoryAndShowroom",
        attributeNodes = {
                @NamedAttributeNode("category"),
                @NamedAttributeNode("showroom"),
        }
)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model", nullable = false, length = 32)
    private String model;

    @Column(name = "brand", nullable = false, length = 32)
    private String brand;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "year", nullable = false)
    private int year;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name ="showroom_id" )
    private CarShowroom showroom;

    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL,orphanRemoval=true,fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> review = new ArrayList<>();
}
