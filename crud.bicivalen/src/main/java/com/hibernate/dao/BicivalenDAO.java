package com.hibernate.dao;


	import java.util.HashSet;
	import java.util.List;
	import java.util.Set;

	import org.hibernate.Session;
	import org.hibernate.Transaction;



	public class BicivalenDAO {

		public List<Producto> selectAllProducto() {
			Session session = ProductoConection.getSessionFactory().openSession();
			Transaction tx = null;
			List<Producto> lista = null;
			try {
				tx = session.beginTransaction();
				lista = session.createQuery("FROM Producto", Producto.class).getResultList();
				tx.commit();
			} catch (Exception e) {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
				e.printStackTrace();
			}
			return lista;
		}

		public void insertProducto(Producto p) {
			Transaction transaction = null;
			try (Session session = ProductoConection.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				session.persist(p);
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			}
		}

		public Producto selectProductoById(int codigo) {
			Transaction transaction = null;
			Producto p = null;
			try (Session session = ProductoConection.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				p = session.get(Producto.class, codigo);
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			}
			return p;
		}

		public void updateProducto(Producto p) {
			Transaction transaction = null;
			try (Session session = ProductoConection.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				session.merge(p);
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
			}
		}

		public void deleteProducto(int id) {
			Transaction transaction = null;
			Producto p = null;
			try (Session session = ProductoConection.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				p = session.get(Producto.class, id);
				session.remove(p);
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			}
		}

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
		        if (tx != null) tx.rollback();
		        e.printStackTrace();
		    }
		    return lista;
		}

}
