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

/**
 * Objeto de Acceso a Datos (DAO) para la aplicación BiciValen.
 * <p>
 * Proporciona operaciones CRUD completas para todas las entidades del dominio:
 * {@link Bicicleta}, {@link Estacion}, {@link Ruta}, {@link Usuario} y {@link Bono}.
 * </p>
 * <p>
 * Todas las operaciones de escritura validan la entidad mediante Bean Validation
 * antes de interactuar con la base de datos. Los puestos libres de cada
 * {@link Estacion} se gestionan automáticamente en {@link #insertBicicleta},
 * {@link #updateBicicleta} y {@link #deleteBicicleta}.
 * </p>
 */
public class BicivalenDAO {

    private static final Validator VALIDATOR;
    static {
        try (ValidatorFactory f = Validation.byDefaultProvider().configure()
                .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
                .buildValidatorFactory()) {
            VALIDATOR = f.getValidator();
        }
    }

    /**
     * Valida una entidad usando las restricciones de Bean Validation definidas
     * en sus anotaciones ({@code @NotBlank}, {@code @Min}, etc.).
     *
     * @param <T>     tipo de la entidad
     * @param entidad objeto a validar
     * @throws IllegalArgumentException si alguna restricción es violada,
     *                                  con los mensajes de error concatenados
     */
    private <T> void validar(T entidad) {
        Set<ConstraintViolation<T>> violaciones = VALIDATOR.validate(entidad);
        if (!violaciones.isEmpty()) {
            String msg = violaciones.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n• ", "• ", ""));
            throw new IllegalArgumentException(msg);
        }
    }

    // -------------------------------------------------------------------------
    // BICICLETA
    // -------------------------------------------------------------------------

    /**
     * Recupera todas las bicicletas almacenadas en la base de datos.
     *
     * @return lista con todas las {@link Bicicleta}; puede estar vacía pero no es {@code null}
     */
    public List<Bicicleta> selectAllBicicleta() {
        Session session = BicivalenConection.getSessionFactory().openSession();
        Transaction tx = null;
        List<Bicicleta> lista = null;
        try {
            tx = session.beginTransaction();
            lista = session.createQuery("FROM Bicicleta", Bicicleta.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca una bicicleta por su identificador.
     *
     * @param codigo identificador primario de la {@link Bicicleta}
     * @return la {@link Bicicleta} encontrada, o {@code null} si no existe
     */
    public Bicicleta selectBicicletaById(int codigo) {
        Transaction transaction = null;
        Bicicleta b = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            b = session.get(Bicicleta.class, codigo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Persiste una nueva bicicleta en la base de datos.
     * <p>
     * Si la bicicleta tiene una {@link Estacion} asignada, se comprueba que
     * haya puestos libres y se descuenta uno automáticamente.
     * </p>
     *
     * @param b bicicleta a insertar (no debe tener id asignado)
     * @throws IllegalArgumentException si la entidad no supera la validación
     * @throws IllegalStateException    si la estación destino no tiene puestos libres
     */
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
            if (transaction != null) transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de una bicicleta existente.
     * <p>
     * Si la estación cambia, los puestos libres se ajustan automáticamente:
     * se libera un puesto en la estación antigua y se ocupa uno en la nueva.
     * Si la estación nueva no tiene puestos libres, la operación se cancela.
     * </p>
     *
     * @param b bicicleta con los datos actualizados (debe tener id válido)
     * @throws IllegalArgumentException si la entidad no supera la validación
     * @throws IllegalStateException    si la estación destino no tiene puestos libres
     */
    public void updateBicicleta(Bicicleta b) {
        validar(b);
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Bicicleta antigua = session.get(Bicicleta.class, b.getId());
            Estacion estAntigua = antigua.getEstacion();
            Estacion estNueva = b.getEstacion() != null
                    ? session.get(Estacion.class, b.getEstacion().getId()) : null;

            int idAntiguo = estAntigua != null ? estAntigua.getId() : -1;
            int idNuevo   = estNueva   != null ? estNueva.getId()   : -1;

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
            if (transaction != null) transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Elimina una bicicleta de la base de datos por su identificador.
     * <p>
     * Si la bicicleta estaba asignada a una estación, se libera su puesto
     * automáticamente antes de borrarla.
     * </p>
     *
     * @param id identificador de la {@link Bicicleta} a eliminar
     */
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
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------
    // ESTACION
    // -------------------------------------------------------------------------

    /**
     * Recupera todas las estaciones almacenadas en la base de datos.
     *
     * @return lista con todas las {@link Estacion}
     */
    public List<Estacion> selectAllEstacion() {
        Session session = BicivalenConection.getSessionFactory().openSession();
        Transaction tx = null;
        List<Estacion> lista = null;
        try {
            tx = session.beginTransaction();
            lista = session.createQuery("FROM Estacion", Estacion.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca una estación por su identificador, inicializando también su colección
     * de bicicletas para evitar {@code LazyInitializationException}.
     *
     * @param codigo identificador primario de la {@link Estacion}
     * @return la {@link Estacion} con sus bicicletas cargadas, o {@code null} si no existe
     */
    public Estacion selectEstacionById(int codigo) {
        Transaction transaction = null;
        Estacion e = null;
        Session session = null;
        try {
            session = BicivalenConection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            e = session.get(Estacion.class, codigo);
            if (e != null) Hibernate.initialize(e.getBicicletas());
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return e;
    }

    /**
     * Persiste una nueva estación en la base de datos.
     *
     * @param e estación a insertar
     * @throws IllegalArgumentException si la entidad no supera la validación
     */
    public void insertEstacion(Estacion e) {
        validar(e);
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(e);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            ex.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de una estación existente.
     *
     * @param e estación con los datos actualizados (debe tener id válido)
     * @throws IllegalArgumentException si la entidad no supera la validación
     */
    public void updateEstacion(Estacion e) {
        validar(e);
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(e);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
        }
    }

    /**
     * Elimina una estación de la base de datos por su identificador.
     *
     * @param id identificador de la {@link Estacion} a eliminar
     */
    public void deleteEstacion(int id) {
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Estacion e = session.get(Estacion.class, id);
            session.remove(e);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            ex.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------
    // RUTA
    // -------------------------------------------------------------------------

    /**
     * Recupera todas las rutas almacenadas en la base de datos.
     *
     * @return lista con todas las {@link Ruta}
     */
    public List<Ruta> selectAllRuta() {
        Session session = BicivalenConection.getSessionFactory().openSession();
        Transaction tx = null;
        List<Ruta> lista = null;
        try {
            tx = session.beginTransaction();
            lista = session.createQuery("FROM Ruta", Ruta.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca una ruta por su identificador.
     *
     * @param codigo identificador primario de la {@link Ruta}
     * @return la {@link Ruta} encontrada, o {@code null} si no existe
     */
    public Ruta selectRutaById(int codigo) {
        Transaction transaction = null;
        Ruta r = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            r = session.get(Ruta.class, codigo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return r;
    }

    /**
     * Persiste una nueva ruta en la base de datos.
     *
     * @param r ruta a insertar
     */
    public void insertRuta(Ruta r) {
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(r);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de una ruta existente.
     *
     * @param r ruta con los datos actualizados (debe tener id válido)
     */
    public void updateRuta(Ruta r) {
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(r);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }

    /**
     * Elimina una ruta de la base de datos por su identificador.
     *
     * @param id identificador de la {@link Ruta} a eliminar
     */
    public void deleteRuta(int id) {
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Ruta r = session.get(Ruta.class, id);
            session.remove(r);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------
    // USUARIO
    // -------------------------------------------------------------------------

    /**
     * Recupera todos los usuarios almacenados en la base de datos.
     *
     * @return lista con todos los {@link Usuario}
     */
    public List<Usuario> selectAllUsuario() {
        Session session = BicivalenConection.getSessionFactory().openSession();
        Transaction tx = null;
        List<Usuario> lista = null;
        try {
            tx = session.beginTransaction();
            lista = session.createQuery("FROM Usuario", Usuario.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param codigo identificador primario del {@link Usuario}
     * @return el {@link Usuario} encontrado, o {@code null} si no existe
     */
    public Usuario selectUsuarioById(int codigo) {
        Transaction transaction = null;
        Usuario u = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            u = session.get(Usuario.class, codigo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return u;
    }

    /**
     * Persiste un nuevo usuario en la base de datos.
     *
     * @param u usuario a insertar
     * @throws IllegalArgumentException si la entidad no supera la validación
     */
    public void insertUsuario(Usuario u) {
        validar(u);
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(u);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param u usuario con los datos actualizados (debe tener id válido)
     */
    public void updateUsuario(Usuario u) {
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(u);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }

    /**
     * Elimina un usuario de la base de datos por su identificador.
     *
     * @param id identificador del {@link Usuario} a eliminar
     */
    public void deleteUsuario(int id) {
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Usuario u = session.get(Usuario.class, id);
            session.remove(u);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------
    // BONO
    // -------------------------------------------------------------------------

    /**
     * Recupera todos los bonos almacenados en la base de datos.
     *
     * @return lista con todos los {@link Bono}
     */
    public List<Bono> selectAllBono() {
        Session session = BicivalenConection.getSessionFactory().openSession();
        Transaction tx = null;
        List<Bono> lista = null;
        try {
            tx = session.beginTransaction();
            lista = session.createQuery("FROM Bono", Bono.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca un bono por su identificador.
     *
     * @param codigo identificador primario del {@link Bono}
     * @return el {@link Bono} encontrado, o {@code null} si no existe
     */
    public Bono selectBonoById(int codigo) {
        Transaction transaction = null;
        Bono bono = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            bono = session.get(Bono.class, codigo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return bono;
    }

    /**
     * Persiste un nuevo bono en la base de datos.
     *
     * @param bono bono a insertar
     * @throws IllegalArgumentException si la entidad no supera la validación
     */
    public void insertBono(Bono bono) {
        validar(bono);
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(bono);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de un bono existente.
     *
     * @param bono bono con los datos actualizados (debe tener id válido)
     * @throws IllegalArgumentException si la entidad no supera la validación
     */
    public void updateBono(Bono bono) {
        validar(bono);
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(bono);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }

    /**
     * Elimina un bono de la base de datos por su identificador.
     *
     * @param id identificador del {@link Bono} a eliminar
     */
    public void deleteBono(int id) {
        Transaction transaction = null;
        try (Session session = BicivalenConection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Bono bono = session.get(Bono.class, id);
            session.remove(bono);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}