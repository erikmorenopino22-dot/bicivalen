package com.hibernate.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.model.Bicicleta;
import com.hibernate.model.Bono;
import com.hibernate.model.Estacion;
import com.hibernate.model.Ruta;
import com.hibernate.model.Usuario;
import com.hibernate.util.BicivalenConection;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class BicivalenDAO {

	private static final Validator VALIDATOR;
	static {
		try (ValidatorFactory f = Validation.byDefaultProvider().configure()
				.messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
				.buildValidatorFactory()) {
			VALIDATOR = f.getValidator();
		}
	}

	private <T> void validar(T entidad) {
		Set<ConstraintViolation<T>> violaciones = VALIDATOR.validate(entidad);
		if (!violaciones.isEmpty()) {
			String msg = violaciones.stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.joining("\n• ", "• ", ""));
			throw new IllegalArgumentException(msg);
		}
	}

	public List<Bicicleta> selectAllBicicleta() {
		Session session = BicivalenConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Bicicleta> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Bicicleta", Bicicleta.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
		}
		return lista;
	}

	public Bicicleta selectBicicletaById(int codigo) {
		Transaction transaction = null;
		Bicicleta b = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			b = session.get(Bicicleta.class, codigo);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return b;
	}

	public void insertBicicleta(Bicicleta b) {
		validar(b);

		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			if (b.getEstacion() != null) {
				Estacion est = session.get(Estacion.class, b.getEstacion().getId());
				if (est.getPuestos() <= 0)
					throw new IllegalStateException("No hay puestos libres en la estación «" + est.getNombre() + "».");
				est.setPuestos(est.getPuestos() - 1);
				b.setEstacion(est);
			}

			session.persist(b);
			transaction.commit();

		} catch (IllegalStateException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void updateBicicleta(Bicicleta b) {
		validar(b);

		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			Bicicleta antigua = session.get(Bicicleta.class, b.getId());
			Estacion estAntigua = antigua.getEstacion();
			Estacion estNueva = b.getEstacion() != null ? session.get(Estacion.class, b.getEstacion().getId()) : null;

			int idAntiguo = estAntigua != null ? estAntigua.getId() : -1;
			int idNuevo = estNueva != null ? estNueva.getId() : -1;

			if (idAntiguo != idNuevo) {
				if (estAntigua != null) {
					Estacion ea = session.get(Estacion.class, estAntigua.getId());
					ea.setPuestos(ea.getPuestos() + 1);
				}
				if (estNueva != null) {
					if (estNueva.getPuestos() <= 0)
						throw new IllegalStateException("No hay puestos libres en «" + estNueva.getNombre() + "».");
					estNueva.setPuestos(estNueva.getPuestos() - 1);
				}
			}

			antigua.setTipo(b.getTipo());
			antigua.setEstado(b.getEstado());
			antigua.setEstacion(estNueva);

			session.merge(antigua);
			transaction.commit();

		} catch (IllegalStateException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void deleteBicicleta(int id) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			Bicicleta b = session.get(Bicicleta.class, id);
			if (b != null) {
				if (b.getEstacion() != null) {
					Estacion est = session.get(Estacion.class, b.getEstacion().getId());
					if (est != null) {
						est.getBicicletas().remove(b);
						est.setPuestos(est.getPuestos() + 1);
					}
					b.setEstacion(null);
				}
				session.remove(b);
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
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

	public Estacion selectEstacionById(int codigo) {
		Transaction transaction = null;
		Estacion e = null;
		Session session = null;
		try {
			session = BicivalenConection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			e = session.get(Estacion.class, codigo);
			if (e != null) {
				Hibernate.initialize(e.getBicicletas());
			}
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return e;
	}

	public void insertEstacion(Estacion e) {
		validar(e);
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

	public void updateEstacion(Estacion e) {
		validar(e);
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

	public void insertUsuario(Usuario u) {
		validar(u);
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

	public void updateUsuario(Usuario u) {
		validar(u);
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

	public void insertBono(Bono bono) {
		validar(bono);
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

	public void updateBono(Bono bono) {
		validar(bono);
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