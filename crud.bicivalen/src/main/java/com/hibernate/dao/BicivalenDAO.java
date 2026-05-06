package com.hibernate.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.model.Bicicleta;
import com.hibernate.model.Bono;
import com.hibernate.model.Estacion;
import com.hibernate.model.Ruta;
import com.hibernate.model.Usuario;
import com.hibernate.util.BicivalenConection;

public class BicivalenDAO {

	public List<Bicicleta> selectAllBicicleta() {
		Session session = BicivalenConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Bicicleta> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Bicicleta", Bicicleta.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return lista;
	}

	public void insertBicicleta(Bicicleta b) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(b);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public Bicicleta selectBicicletaById(int codigo) {
		Transaction transaction = null;
		Bicicleta b = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			b = session.get(Bicicleta.class, codigo);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return b;
	}

	public void updateBicicleta(Bicicleta b) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(b);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void deleteBicicleta(int id) {
		Transaction transaction = null;
		Bicicleta b = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			b = session.get(Bicicleta.class, id);
			session.remove(b);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public List<Estacion> selectAllEstacion() {
		Session session = BicivalenConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Estacion> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Estacion", Estacion.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public void insertEstacion(Estacion e) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(e);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null)
				transaction.rollback();
			ex.printStackTrace();
		}
	}

	public Estacion selectEstacionById(int codigo) {
		Transaction transaction = null;
		Estacion e = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			e = session.get(Estacion.class, codigo);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null)
				transaction.rollback();
			ex.printStackTrace();
		}
		return e;
	}

	public void updateEstacion(Estacion e) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(e);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null)
				transaction.rollback();
		}
	}

	public void deleteEstacion(int id) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Estacion e = session.get(Estacion.class, id);
			session.remove(e);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null)
				transaction.rollback();
			ex.printStackTrace();
		}
	}

	public List<Ruta> selectAllRuta() {
		Session session = BicivalenConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Ruta> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Ruta", Ruta.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public void insertRuta(Ruta r) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(r);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public Ruta selectRutaById(int codigo) {
		Transaction transaction = null;
		Ruta r = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			r = session.get(Ruta.class, codigo);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return r;
	}

	public void updateRuta(Ruta r) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(r);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}
	}

	public void deleteRuta(int id) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Ruta r = session.get(Ruta.class, id);
			session.remove(r);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Usuario> selectAllUsuario() {
		Session session = BicivalenConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Usuario> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Usuario", Usuario.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public void insertUsuario(Usuario u) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(u);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public Usuario selectUsuarioById(int codigo) {
		Transaction transaction = null;
		Usuario u = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			u = session.get(Usuario.class, codigo);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return u;
	}

	public void updateUsuario(Usuario u) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(u);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}
	}

	public void deleteUsuario(int id) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Usuario u = session.get(Usuario.class, id);
			session.remove(u);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Bono> selectAllBono() {
		Session session = BicivalenConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Bono> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Bono", Bono.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public void insertBono(Bono bono) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(bono);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public Bono selectBonoById(int codigo) {
		Transaction transaction = null;
		Bono bono = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			bono = session.get(Bono.class, codigo);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return bono;
	}

	public void updateBono(Bono bono) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(bono);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}
	}

	public void deleteBono(int id) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Bono bono = session.get(Bono.class, id);
			session.remove(bono);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}
}
