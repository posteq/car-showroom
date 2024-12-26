package by.clevertec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reviews")
@Entity
@ToString(exclude = {"client"})
@NamedEntityGraph(
        name = "withClientShowroomAndCategory",
        attributeNodes = {
                @NamedAttributeNode("client"),

                @NamedAttributeNode(value = "car" , subgraph = "showroom"),
                @NamedAttributeNode(value = "car" , subgraph = "category"),
        },
        subgraphs = {
                @NamedSubgraph(name = "showroom", attributeNodes = {@NamedAttributeNode("showroom")}),
                @NamedSubgraph(name = "category", attributeNodes = {@NamedAttributeNode("category")})
        }
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

}
