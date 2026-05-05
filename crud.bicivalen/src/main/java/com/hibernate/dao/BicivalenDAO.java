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

	public void insertBono(Bono b) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(b);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public Bono selectBonoById(int codigo) {
		Transaction transaction = null;
		Bono b = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			b = session.get(Bono.class, codigo);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return b;
	}

	public void updateBono(Bono b) {
		Transaction transaction = null;
		try (Session session = BicivalenConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(b);
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
			Bono b = session.get(Bono.class, id);
			session.remove(b);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	//

	public List<Producto> getProductosSinStock(int cantidad) {
		Transaction transaction = null;
		List<Producto> producto = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			producto = session.createQuery("FROM Producto WHERE stock = :cantidad", Producto.class)
					.setParameter("cantidad", cantidad).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw new RuntimeException("Error al obtener ciudades con menos habitantes", e);
		}

		return producto;
	}

	public List<Producto> getProductosCaros(int precio) {
		Transaction transaction = null;
		List<Producto> producto = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			producto = session.createQuery("FROM Producto WHERE precio > :precio", Producto.class)
					.setParameter("precio", precio).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw new RuntimeException("Error al obtener productos caros", e);
		}
		return producto;
	}

	public List<Producto> getProductosbaratos(int precio) {
		Transaction transaction = null;
		List<Producto> producto = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			producto = session.createQuery("FROM Producto WHERE precio < :precio", Producto.class)
					.setParameter("precio", precio).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw new RuntimeException("Error al obtener productos baratos", e);
		}
		return producto;
	}

	public int comprarProducto(int codigo, int unidades) {
		Transaction transaction = null;
		int total = 0;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto p = session.get(Producto.class, codigo);
			if (p != null) {
				int stockDisponible = p.getStock();
				int precio = p.getPrecio();
				int unidadesVendidas = Math.min(unidades, stockDisponible);
				total = unidadesVendidas * precio;
				p.setStock(stockDisponible - unidadesVendidas);
				session.merge(p);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return total;
	}

	public int devoluciónProducto(int codigo, int unidades) {
		Transaction transaction = null;
		int total = 0;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto p = session.get(Producto.class, codigo);
			if (p != null) {
				int stockDisponible = p.getStock();
				int unidadesVendidas = Math.min(unidades, stockDisponible);
				p.setStock(stockDisponible + unidadesVendidas);
				session.merge(p);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return total;
	}

	public List<Distribuidor> selectAllDistribuidores() {
		Session session = ProductoConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Distribuidor> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Distribuidor", Distribuidor.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return lista;
	}

	public void insertDistribuidor(Distribuidor d) {
		Transaction transaction = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(d);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public Distribuidor selectDistribuidorById(int id) {
		Transaction transaction = null;
		Distribuidor d = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			d = session.get(Distribuidor.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return d;
	}

	public void updateDistribuidor(Distribuidor d) {
		Transaction transaction = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(d);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void deleteDistribuidor(int id) {
		Transaction transaction = null;
		Distribuidor d = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			d = session.get(Distribuidor.class, id);
			session.remove(d);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void addRelacion(int idProducto, int idDistribuidor) {
		Transaction transaction = null;
		Session session = null;

		try {
			session = ProductoConection.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Producto producto = session.get(Producto.class, idProducto);
			Distribuidor distribuidor = session.get(Distribuidor.class, idDistribuidor);
			if (producto != null && distribuidor != null) {
				producto.addDistribuidor(distribuidor);
				session.merge(producto);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void deleteRelacion(int idProducto, int idDistribuidor) {
		Transaction transaction = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto producto = session.get(Producto.class, idProducto);
			Distribuidor distribuidor = session.get(Distribuidor.class, idDistribuidor);
			if (producto != null && distribuidor != null) {
				producto.removeDistribuidor(distribuidor);
				session.merge(producto);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Producto> getProductosConDistribuidores() {
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			return session
					.createQuery("SELECT DISTINCT p FROM Producto p LEFT JOIN FETCH p.distribuidores", Producto.class)
					.getResultList();
		}
	}

	public List<Clientes> selectAllCliente() {
		Session session = ProductoConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Clientes> lista = null;
		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Clientes", Clientes.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return lista;
	}

	public void insertCliente(Clientes c) {
		Transaction transaction = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(c);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void updateCliente(Clientes c) {
		Transaction transaction = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(c);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void deleteCliente(int id) {
		Transaction transaction = null;
		Clientes c = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			c = session.get(Clientes.class, id);
			session.remove(c);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public Clientes selectClienteById(int id) {
		Transaction transaction = null;
		Clientes c = null;
		try (Session session = ProductoConection.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			c = session.get(Clientes.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return c;
	}

	public void addCompra(int idCliente, int idProducto, int unidades) {
		Session session = null;
		Transaction tx = null;
		try {
			session = ProductoConection.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Clientes cliente = session.get(Clientes.class, idCliente);
			Producto producto = session.get(Producto.class, idProducto);
			if (cliente != null && producto != null) {
				Compra compra = new Compra();
				compra.setCliente(cliente);
				compra.setProducto(producto);
				compra.setUnidades(unidades);
				session.persist(compra);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();

		} finally {
			if (session != null)
				session.close();
		}
	}

	public void deleteCompra(int idCompra) {
		Session session = null;
		Transaction tx = null;
		try {
			session = ProductoConection.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Compra compra = session.get(Compra.class, idCompra);
			if (compra != null) {
				session.remove(compra);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public List<Compra> selectAllCompras() {
		Session session = ProductoConection.getSessionFactory().openSession();
		Transaction tx = null;
		List<Compra> lista = null;

		try {
			tx = session.beginTransaction();
			lista = session.createQuery("FROM Compra", Compra.class).getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return lista;
	}

}
