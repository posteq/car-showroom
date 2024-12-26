package by.clevertec.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
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
@Table(name = "car_showrooms")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cars"})
@Builder
@Entity
@NamedEntityGraph(
        name = "withCarsAndCategory",
        attributeNodes = {
                @NamedAttributeNode(value = "cars", subgraph = "cars.category")
        },
        subgraphs = {
                @NamedSubgraph(name = "cars.category", attributeNodes = @NamedAttributeNode("category"))
        }
)
public class CarShowroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "address", nullable = false, length = 32)
    private String address;

    @Builder.Default
    @OneToMany(mappedBy = "showroom", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();
}
