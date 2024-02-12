package org.example.dao;

import org.example.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class TaskDao {
    private final SessionFactory sessionFactory;

    public TaskDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTask(int offset, int limit) {
        String hql = "SELECT t FROM Task AS t";
        Session session = sessionFactory.getCurrentSession();
        Query<Task> query = session.createQuery(hql, Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public int getAllCountTask() {
        String hql = "SELECT count (t) FROM Task AS t";
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(hql);
        return Math.toIntExact(query.getSingleResult());
    }

    @Transactional(readOnly = true)
    public Task getTaskById(int id) {
        String hql = "SELECT t FROM Task AS t WHERE t.id = :id";
        Session session = sessionFactory.getCurrentSession();
        Query<Task> query = session.createQuery(hql, Task.class);
        query.setParameter("id", id);

        return query.uniqueResult();
    }

    @Transactional()
    public Task saveOrupdateTask(Task t) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(t);
    }

    @Transactional()
    public void deleteTask(Task t) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(t);
    }


}
