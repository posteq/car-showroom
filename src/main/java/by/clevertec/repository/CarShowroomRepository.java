package by.clevertec.repository;

import by.clevertec.entity.CarShowroom;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;

import java.util.List;
import java.util.Map;

public class CarShowroomRepository implements BaseRepository<CarShowroom,Long> {

    @Override
    public CarShowroom findById(Long id) {
        try(Session session = HibernateUtil.getSession()) {
            return session.get(CarShowroom.class, id);
        }
    }

    @Override
    public List<CarShowroom> findAll() {
        try(Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM CarShowroom",CarShowroom.class)
                    .list();
        }
    }

    @Override
    public CarShowroom create(CarShowroom carShowroom) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx= session.beginTransaction();
            session.persist(carShowroom);
            tx.commit();
            return carShowroom;
        }catch (Exception e) {
            if (tx != null) {
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
            CarShowroom foundedCarShowroom = session.get(CarShowroom.class, id);
            if(foundedCarShowroom!=null) {
                session.delete(foundedCarShowroom);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public CarShowroom update(CarShowroom carShowroom) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.update(carShowroom);
            tx.commit();
            return carShowroom;
        }catch (Exception e) {
            if(tx!=null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<CarShowroom> findAllShowroomWithCars() {
        try(Session session = HibernateUtil.getSession()) {

            RootGraph<CarShowroom> showroomGraph = session.createEntityGraph(CarShowroom.class);
            showroomGraph.addAttributeNode("cars");
            showroomGraph.addAttributeNode("category");
            RootGraph<?> graph = session.getEntityGraph("withCarsAndCategory");

            Map<String, ? extends RootGraph<?>> property = Map.of(GraphSemantic.LOAD.getJpaHintName(), graph);

            return session.createQuery("SELECT c FROM CarShowroom c ", CarShowroom.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), property)
                    .list();

        }
    }

}
