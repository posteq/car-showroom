package by.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

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
