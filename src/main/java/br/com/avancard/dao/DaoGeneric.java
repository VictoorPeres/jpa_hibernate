package br.com.avancard.dao;

import br.com.avancard.jpa_hibernate.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DaoGeneric<E> {
    private EntityManager entityManager = new HibernateUtil().getEntityManager();

    public void salvar(E entidade) {
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        entityManager.persist(entidade);
        transiction.commit();
    }

    public E listar(Class<E> entidade, Long id){
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        E e = (E) entityManager.find(entidade, id);
        transiction.commit();

        return e;
    }

    public List<E> listarAll(Class<E> entidade){
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
        transiction.commit();
        return lista;
    }

    public E atualizar(E entidade){
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        entityManager.merge(entidade);
        transiction.commit();
        return entidade;
    }

    public void excluir(E entidade){
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        entityManager.remove(entidade);
        transiction.commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }


}
