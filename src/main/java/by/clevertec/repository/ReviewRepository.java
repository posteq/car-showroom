package by.clevertec.repository;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.entity.Category;
import by.clevertec.entity.Client;
import by.clevertec.entity.Review;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;

import java.util.List;

public class ReviewRepository implements BaseRepository<Review,Long>{

    @Override
    public Review findById(Long id) {
        try(Session session = HibernateUtil.getSession()) {
            return session.get(Review.class, id);
        }
    }

    @Override
    public List<Review> findAll() {
        try(Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Review",Review.class)
                    .list();
        }
    }

    @Override
    public Review create(Review review) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx= session.beginTransaction();
            session.persist(review);
            tx.commit();
            return review;
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Review update(Review review) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.update(review);
            tx.commit();
            return review;
        }catch (Exception e) {
            if(tx!=null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            Review foundedReview = session.get(Review.class, id);
            if(foundedReview!=null) {
                session.delete(foundedReview);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<Review> searchByKeyword(String keyword){

        try(Session session = HibernateUtil.getSession()) {
            String hql = "SELECT r FROM Review r " +
                    "JOIN FETCH r.client " +
                    "JOIN FETCH r.car c " +
                    "JOIN FETCH c.showroom " +
                    "JOIN FETCH c.category " +
                    "WHERE r.content LIKE :keyword";

            return session.createQuery(hql, Review.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .list();
        }
    }

    public List<Review> searchByRating(int rating){

        try(Session session = HibernateUtil.getSession()) {
            RootGraph<?> graph = session.getEntityGraph("withClientShowroomAndCategory");
            SubGraph<Car> carSubgraph = graph.addSubgraph("car", Car.class);
            graph.addAttributeNodes("client");

            carSubgraph.addSubgraph("showroom", CarShowroom.class);
            carSubgraph.addSubgraph("category", Category.class);

            return session.createQuery("SELECT r FROM Review r WHERE r.rating =:rating ", Review.class)
                    .setParameter("rating",rating)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(),graph)
                    .list();
        }
    }

    public Review addReview(Client client, Car car, String text, int rating) {

        if (client == null || car == null) {
            throw new IllegalArgumentException("Client or Car cannot be null");
        }

        Review newReview = Review.builder()
                .car(car)
                .client(client)
                .content(text)
                .rating(rating)
                .build();

        return create(newReview);
    }

}
