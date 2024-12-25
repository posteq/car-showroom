package by.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model", nullable = false, length = 32)
    private String model;

    @Column(name = "brand", nullable = false, length = 32)
    private String brand;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "years_of_release", nullable = false)
    private Integer yearsOfRelease;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne()
    @JoinColumn(name ="car_showroom_id" )
    private CarShowroom showroom;

    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL,orphanRemoval=true,fetch = FetchType.LAZY)
    @Column(name = "review")
    @Builder.Default
    private List<Review> review = new ArrayList<>();
}
