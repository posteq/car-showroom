package by.clevertec.repository;

import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientRepository implements BaseRepository<Client,Long> {

    @Override
    public Client findById(Long id) {
        try(Session session = HibernateUtil.getSession()) {
            return session.get(Client.class, id);
        }
    }

    @Override
    public List<Client> findAll() {
        try(Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Client",Client.class)
                    .list();
        }
    }

    @Override
    public Client create(Client client) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx= session.beginTransaction();
            session.persist(client);
            tx.commit();
            return client;
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Client update(Client client) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.update(client);
            tx.commit();
            return client;
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
            Client foundedClient = session.get(Client.class, id);
            if(foundedClient!=null) {
                session.delete(foundedClient);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public Client buyCar(Client client, Car car){
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            Client foundedClient = session.find(Client.class,client.getId());
            Car foundedCar = session.find(Car.class,car.getId());
            if(foundedClient!=null && foundedCar != null) {
                foundedCar.setShowroom(null);
                foundedClient.getCars().add(foundedCar);
            }
            session.update(foundedClient);
            tx.commit();
            return foundedClient;
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
