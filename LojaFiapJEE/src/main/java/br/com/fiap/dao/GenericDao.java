package br.com.fiap.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;


public class GenericDao<T> implements Dao<T> {

	private  Class<T> classe;


	Transaction trns = null;

	public GenericDao(Class<T> classe) {
		this.classe = classe;
	}

	@Override
	public void adicionar(T entidade) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			trns = session.beginTransaction();
			session.save(entidade);
			session.getTransaction().commit();


		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			try {
				throw new Exception("Error ao adicionar entiadde: "+entidade.getClass().toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listar() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createQuery("From " + classe.getSimpleName()).setCacheable(true).list();
	}

	@Override
	public T buscarById(Long id) {
		T entidade;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			entidade =  classe.cast(session.get(classe, id) );
		} finally {
			session.flush();
			session.close();
		}
		return entidade;
	}
	@Override
	public T buscarById(String id) {
		T entidade;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			entidade =  classe.cast(session.get(classe, id));
		} finally {
			session.flush();
			session.close();
		}
		return entidade;
	}
	@Override
	public  void deletarById(Long id) {
		T entidade = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			entidade =  classe.cast(session.get(classe, id));

			session.delete(entidade);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			try {
				throw new Exception("Error ao excluir entidade");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			session.flush();
			session.close();
		}
	}
	@Override
	public  void deletarById(String id) {
		T entidade = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();
			entidade =  classe.cast(session.get(classe, id));

			session.delete(entidade);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			try {
				throw new Exception("Error ao excluir entidade");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	@Override
	public List<T> pesquisar( String value, String prop){

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<T> list = null;
		try {

			Criteria query = session.createCriteria(classe).setCacheable(true);



			query.add(Restrictions.like(prop, value, MatchMode.ANYWHERE));
			System.out.println("QUERY::: "+query.toString());




			list = query.list();


		} catch (RuntimeException e) {

			e.printStackTrace();

		} finally {

			session.flush();
			session.close();

		}
		return list;

	}

	@Override
	public void update(T entidade){
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			trns = session.beginTransaction();


			session.update(entidade);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			try {
				throw new Exception("Error ao atualizar entidade "+entidade.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			session.flush();
			session.close();
		}

	}







}
