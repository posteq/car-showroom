package by.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
