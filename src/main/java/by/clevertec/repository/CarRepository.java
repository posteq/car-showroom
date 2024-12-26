package by.clevertec.repository;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements BaseRepository<Car,Long>{

    @Override
    public Car findById(Long id) {
        try(Session session = HibernateUtil.getSession()) {
            return session.get(Car.class, id);
        }
    }

    @Override
    public List<Car> findAll() {
        try(Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Car",Car.class)
                    .setHint("org.hibernate.cacheable", true)
                    .list();
        }
    }

    @Override
    public Car create(Car car) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx= session.beginTransaction();
            session.persist(car);
            tx.commit();
            return car;
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Car update(Car car) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.update(car);
            tx.commit();
            return car;
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
            Car foundedCar = session.get(Car.class, id);
            if(foundedCar!=null) {
                session.delete(foundedCar);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void assignToShowroom(Car car, CarShowroom carShowroom) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            Car foundCar = findById(car.getId());
            CarShowroom newShowroom = session.find(CarShowroom.class,carShowroom.getId());

            if(foundCar != null && newShowroom != null) {
                foundCar.setShowroom(newShowroom);
                session.update(foundCar);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<Car> findByFilter(String brand, Integer year, String category,Double minPrice, Double maxPrice) {

        try (Session session = HibernateUtil.getSession()){

            RootGraph<?> graph = getRootGraph(session);

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
            Root<Car> car = criteria.from(Car.class);

            ArrayList<Predicate> predicates = new ArrayList<>();

            if(brand != null)
                predicates.add(cb.equal(car.get("brand"),brand));
            if(year != null)
                predicates.add(cb.equal(car.get("yearsOfRelease"),year));
            if(category != null)
                predicates.add(cb.equal(car.get("category").get("name"),category));
            if(minPrice != null)
                predicates.add(cb.gt(car.get("price"),minPrice));
            if(maxPrice != null)
                predicates.add(cb.le(car.get("price"),maxPrice));

            criteria.select(car)
                    .where(predicates.toArray(Predicate[]::new));

            return session.createQuery(criteria)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(),graph)
                    .list();
        }
    }

    public List<Car> sortByPrice(boolean forwardOrder){

        try (Session session = HibernateUtil.getSession()){

            RootGraph<?> graph = getRootGraph(session);

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
            Root<Car> car = criteria.from(Car.class);

            if (forwardOrder) {
                criteria.orderBy(cb.asc(car.get("price")));
            } else {
                criteria.orderBy(cb.desc(car.get("price")));
            }
            return session.createQuery(criteria)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(),graph)
                    .list();
        }
    }

    public List<Car> findCarsWithPagination(int page, int pageSize){
        try (Session session = HibernateUtil.getSession()){

            RootGraph<?> graph = getRootGraph(session);

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
            Root<Car> car = criteria.from(Car.class);

            criteria.select(car);

            return session.createQuery(criteria)
                    .setFirstResult((page-1)*pageSize)
                    .setMaxResults(pageSize)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(),graph)
                    .list();
        }
    }

    private static RootGraph<?> getRootGraph(Session session) {
        RootGraph<?> graph = session.getEntityGraph("withCategoryAndShowroom");
        graph.addAttributeNodes("category","showroom");
        return graph;
    }
}
