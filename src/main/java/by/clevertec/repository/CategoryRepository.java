package by.clevertec.repository;

import by.clevertec.entity.Category;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CategoryRepository implements BaseRepository<Category,Long> {

    public Category getByName(String name) {
        try(Session session = HibernateUtil.getSession()) {
            return session.createQuery("SELECT c FROM Category c WHERE c.name =:name", Category.class)
                    .setParameter("name",name)
                    .getSingleResult();
        }
    }

    @Override
    public Category findById(Long id) {
        try(Session session = HibernateUtil.getSession()) {
            return session.get(Category.class, id);
        }
    }

    @Override
    public List<Category> findAll() {
        try(Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Category", Category.class).list();
        }
    }

    @Override
    public Category create(Category category) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx= session.beginTransaction();
            session.persist(category);
            tx.commit();
            return category;
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Category update(Category category) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.update(category);
            tx.commit();
            return category;
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
            Category foundedCategory = session.get(Category.class, id);
            if(foundedCategory!=null) {
                session.delete(foundedCategory);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
