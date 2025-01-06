package br.com.avancard.dao;

import br.com.avancard.jpa_hibernate.HibernateUtil;
import br.com.avancard.model.TelefonePessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class DaoGeneric<E> {
    private EntityManager entityManager = new HibernateUtil().getEntityManager();

    public void salvar(E entidade) {
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        try{
            entityManager.persist(entidade);
            transiction.commit();
        }catch(Exception e){
            transiction.rollback();
        }
    }

    public void salvarTel(E entidade) {
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

    public List<TelefonePessoa> listarPorId(long id){
        String jpql = "select t from TelefonePessoa t where t.pessoa.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", id);
        return (List<TelefonePessoa>) query.getResultList();
    }


    public E atualizar(E entidade){
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        try{
            entityManager.merge(entidade);
            transiction.commit();
        }catch(Exception e){
            transiction.rollback();
        }
        return entidade;
    }

    public void excluir(E entidade){
        EntityTransaction transiction = entityManager.getTransaction();
        transiction.begin();
        try {
            if(!entityManager.contains(entidade)){
                entidade = entityManager.merge(entidade);
            }
            entityManager.remove(entidade);
            transiction.commit();
        }catch (Exception e){
            transiction.rollback();
        }

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }


}
