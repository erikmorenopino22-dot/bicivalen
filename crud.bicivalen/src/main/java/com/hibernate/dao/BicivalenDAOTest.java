package com.hibernate.dao;

import com.hibernate.model.Bicicleta;
import com.hibernate.model.Bono;
import com.hibernate.model.Estacion;
import com.hibernate.model.Ruta;
import com.hibernate.model.Usuario;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para {@link BicivalenDAO}.
 * <p>
 * Requieren una base de datos accesible configurada en {@code hibernate.cfg.xml}.
 * Cada test limpia los datos que crea para no dejar estado residual.
 * </p>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BicivalenDAOTest {

    private static BicivalenDAO dao;

    @BeforeAll
    static void init() {
        dao = new BicivalenDAO();
    }

    // =========================================================================
    // ESTACION
    // =========================================================================

    /**
     * Verifica que se puede insertar una estación y recuperarla por id.
     */
    @Test
    @Order(1)
    void testInsertYSelectEstacion() {
        Estacion est = Estacion.builder()
                .nombre("Estación Test")
                .ubicacion("Calle Test 1")
                .capacidad(10)
                .puestos(10)
                .build();

        dao.insertEstacion(est);
        assertTrue(est.getId() > 0, "El id debe haberse asignado tras persistir");

        Estacion recuperada = dao.selectEstacionById(est.getId());
        assertNotNull(recuperada);
        assertEquals("Estación Test", recuperada.getNombre());
        assertEquals(10, recuperada.getPuestos());

        dao.deleteEstacion(est.getId());
    }

    /**
     * Verifica que insertar una estación con nombre vacío lanza
     * {@link IllegalArgumentException}.
     */
    @Test
    @Order(2)
    void testInsertEstacionNombreVacioLanzaExcepcion() {
        Estacion est = Estacion.builder()
                .nombre("")
                .ubicacion("Calle Test")
                .capacidad(5)
                .puestos(5)
                .build();

        assertThrows(IllegalArgumentException.class, () -> dao.insertEstacion(est));
    }

    /**
     * Verifica que {@link BicivalenDAO#selectAllEstacion()} devuelve una lista
     * no nula.
     */
    @Test
    @Order(3)
    void testSelectAllEstacionNoNull() {
        List<Estacion> lista = dao.selectAllEstacion();
        assertNotNull(lista);
    }

    /**
     * Verifica que actualizar el nombre de una estación persiste el cambio.
     */
    @Test
    @Order(4)
    void testUpdateEstacion() {
        Estacion est = Estacion.builder()
                .nombre("Est Original")
                .ubicacion("Ubicación")
                .capacidad(5)
                .puestos(5)
                .build();
        dao.insertEstacion(est);

        est.setNombre("Est Actualizada");
        dao.updateEstacion(est);

        Estacion actualizada = dao.selectEstacionById(est.getId());
        assertEquals("Est Actualizada", actualizada.getNombre());

        dao.deleteEstacion(est.getId());
    }

    // =========================================================================
    // BICICLETA — puestos
    // =========================================================================

    /**
     * Verifica que al insertar una bicicleta en una estación se resta un puesto.
     */
    @Test
    @Order(5)
    void testInsertBicicletaRestaUnPuesto() {
        Estacion est = Estacion.builder()
                .nombre("Est Puestos")
                .ubicacion("Ubicación")
                .capacidad(5)
                .puestos(5)
                .build();
        dao.insertEstacion(est);

        Bicicleta bici = Bicicleta.builder()
                .tipo("Urbana")
                .estado("Disponible")
                .estadoUso("Libre")
                .estacion(est)
                .build();
        dao.insertBicicleta(bici);

        Estacion actualizada = dao.selectEstacionById(est.getId());
        assertEquals(4, actualizada.getPuestos(), "Debe quedar 1 puesto menos");

        dao.deleteBicicleta(bici.getId());
        dao.deleteEstacion(est.getId());
    }

    /**
     * Verifica que insertar una bicicleta en una estación sin puestos libres
     * lanza {@link IllegalStateException}.
     */
    @Test
    @Order(6)
    void testInsertBicicletaEstacionLlenaLanzaExcepcion() {
        Estacion est = Estacion.builder()
                .nombre("Est Llena")
                .ubicacion("Ubicación")
                .capacidad(1)
                .puestos(0)   // sin puestos libres
                .build();
        dao.insertEstacion(est);

        Bicicleta bici = Bicicleta.builder()
                .tipo("Electrica")
                .estado("Disponible")
                .estadoUso("Libre")
                .estacion(est)
                .build();

        assertThrows(IllegalStateException.class, () -> dao.insertBicicleta(bici));

        dao.deleteEstacion(est.getId());
    }

    /**
     * Verifica que al eliminar una bicicleta de una estación se libera su puesto.
     */
    @Test
    @Order(7)
    void testDeleteBicicletaLiberaPuesto() {
        Estacion est = Estacion.builder()
                .nombre("Est Delete")
                .ubicacion("Ubicación")
                .capacidad(3)
                .puestos(3)
                .build();
        dao.insertEstacion(est);

        Bicicleta bici = Bicicleta.builder()
                .tipo("Urbana")
                .estado("Disponible")
                .estadoUso("Libre")
                .estacion(est)
                .build();
        dao.insertBicicleta(bici);

        // Tras insertar: puestos = 2
        dao.deleteBicicleta(bici.getId());

        // Tras eliminar: debe volver a 3
        Estacion tras = dao.selectEstacionById(est.getId());
        assertEquals(3, tras.getPuestos(), "El puesto debe haberse liberado");

        dao.deleteEstacion(est.getId());
    }

    /**
     * Verifica que al mover una bicicleta de estación los puestos se ajustan
     * correctamente en ambas estaciones.
     */
    @Test
    @Order(8)
    void testUpdateBicicletaCambioEstacionAjustaPuestos() {
        Estacion estA = Estacion.builder()
                .nombre("Est A")
                .ubicacion("Loc A")
                .capacidad(5)
                .puestos(5)
                .build();
        Estacion estB = Estacion.builder()
                .nombre("Est B")
                .ubicacion("Loc B")
                .capacidad(5)
                .puestos(5)
                .build();
        dao.insertEstacion(estA);
        dao.insertEstacion(estB);

        Bicicleta bici = Bicicleta.builder()
                .tipo("Urbana")
                .estado("Disponible")
                .estadoUso("Libre")
                .estacion(estA)
                .build();
        dao.insertBicicleta(bici); // estA: 4 puestos

        // Mover bici de estA a estB
        bici.setEstacion(estB);
        dao.updateBicicleta(bici);

        Estacion aActualizada = dao.selectEstacionById(estA.getId());
        Estacion bActualizada = dao.selectEstacionById(estB.getId());

        assertEquals(5, aActualizada.getPuestos(), "EstA debe haber recuperado su puesto");
        assertEquals(4, bActualizada.getPuestos(), "EstB debe haber perdido un puesto");

        dao.deleteBicicleta(bici.getId());
        dao.deleteEstacion(estA.getId());
        dao.deleteEstacion(estB.getId());
    }

    /**
     * Simula el flujo de alquiler y devolución: bici sale (estación → null)
     * y vuelve a la misma estación (null → estación). Los puestos deben
     * quedar igual que al principio.
     */
    @Test
    @Order(9)
    void testAlquilerYDevolucionMismaEstacionPuestosIguales() {
        Estacion est = Estacion.builder()
                .nombre("Est Alquiler")
                .ubicacion("Loc")
                .capacidad(3)
                .puestos(3)
                .build();
        dao.insertEstacion(est);

        Bicicleta bici = Bicicleta.builder()
                .tipo("Urbana")
                .estado("Disponible")
                .estadoUso("Libre")
                .estacion(est)
                .build();
        dao.insertBicicleta(bici); // puestos → 2

        // Alquiler: bici sale
        bici.setEstacion(null);
        dao.updateBicicleta(bici); // puestos → 3

        Estacion trasSalida = dao.selectEstacionById(est.getId());
        assertEquals(3, trasSalida.getPuestos(), "Al alquilar debe liberarse el puesto");

        // Devolución: bici vuelve
        bici.setEstacion(est);
        dao.updateBicicleta(bici); // puestos → 2

        Estacion trasEntrada = dao.selectEstacionById(est.getId());
        assertEquals(2, trasEntrada.getPuestos(), "Al devolver debe ocuparse el puesto");

        dao.deleteBicicleta(bici.getId());
        dao.deleteEstacion(est.getId());
    }

    // =========================================================================
    // USUARIO
    // =========================================================================

    /**
     * Verifica el ciclo completo insert → select → update → delete de un usuario.
     */
    @Test
    @Order(10)
    void testCrudUsuario() {
        Usuario u = Usuario.builder()
                .nombre("Test User")
                .email("test@bici.com")
                .contraseña("segura123")
                .build();

        dao.insertUsuario(u);
        assertTrue(u.getId() > 0);

        Usuario recuperado = dao.selectUsuarioById(u.getId());
        assertNotNull(recuperado);
        assertEquals("test@bici.com", recuperado.getEmail());

        recuperado.setEmail("nuevo@bici.com");
        dao.updateUsuario(recuperado);
        assertEquals("nuevo@bici.com", dao.selectUsuarioById(u.getId()).getEmail());

        dao.deleteUsuario(u.getId());
        assertNull(dao.selectUsuarioById(u.getId()), "El usuario debe haberse eliminado");
    }

    // =========================================================================
    // BONO
    // =========================================================================

    /**
     * Verifica el ciclo completo insert → select → delete de un bono.
     */
    @Test
    @Order(11)
    void testInsertYDeleteBono() {
        Bono bono = Bono.builder()
                .nombre("Bono Test")
                .tipo("1 Mes")
                .descripcion("Bono de prueba")
                .build();

        dao.insertBono(bono);
        assertTrue(bono.getId() > 0);

        Bono recuperado = dao.selectBonoById(bono.getId());
        assertNotNull(recuperado);
        assertEquals("Bono Test", recuperado.getNombre());

        dao.deleteBono(bono.getId());
        assertNull(dao.selectBonoById(bono.getId()));
    }

    // =========================================================================
    // RUTA
    // =========================================================================

    /**
     * Verifica que se puede insertar una ruta y recuperarla por id.
     */
    @Test
    @Order(12)
    void testInsertYSelectRuta() {
        Usuario u = Usuario.builder()
                .nombre("Rutero")
                .email("ruta@bici.com")
                .contraseña("pass123")
                .build();
        dao.insertUsuario(u);

        Estacion estI = Estacion.builder()
                .nombre("Inicio")
                .ubicacion("Loc I")
                .capacidad(5)
                .puestos(5)
                .build();
        Estacion estD = Estacion.builder()
                .nombre("Destino")
                .ubicacion("Loc D")
                .capacidad(5)
                .puestos(5)
                .build();
        dao.insertEstacion(estI);
        dao.insertEstacion(estD);

        Ruta ruta = Ruta.builder()
                .usuario(u)
                .estacionI(estI)
                .estacionD(estD)
                .duracion(25)
                .valoracion(4)
                .build();
        dao.insertRuta(ruta);
        assertTrue(ruta.getId() > 0);

        Ruta recuperada = dao.selectRutaById(ruta.getId());
        assertNotNull(recuperada);
        assertEquals(25, recuperada.getDuracion());
        assertEquals(4, recuperada.getValoracion());

        dao.deleteRuta(ruta.getId());
        dao.deleteUsuario(u.getId());
        dao.deleteEstacion(estI.getId());
        dao.deleteEstacion(estD.getId());
    }
}