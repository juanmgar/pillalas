package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.PillalasAlVueloApp;
import com.juanmagarcia.pillalas.domain.Avistamiento;
import com.juanmagarcia.pillalas.repository.AvistamientoRepository;
import com.juanmagarcia.pillalas.service.AvistamientoService;
import com.juanmagarcia.pillalas.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.juanmagarcia.pillalas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AvistamientoResource} REST controller.
 */
@SpringBootTest(classes = PillalasAlVueloApp.class)
public class AvistamientoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private AvistamientoRepository avistamientoRepository;

    @Mock
    private AvistamientoRepository avistamientoRepositoryMock;

    @Mock
    private AvistamientoService avistamientoServiceMock;

    @Autowired
    private AvistamientoService avistamientoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAvistamientoMockMvc;

    private Avistamiento avistamiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvistamientoResource avistamientoResource = new AvistamientoResource(avistamientoService);
        this.restAvistamientoMockMvc = MockMvcBuilders.standaloneSetup(avistamientoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avistamiento createEntity(EntityManager em) {
        Avistamiento avistamiento = new Avistamiento()
            .nombre(DEFAULT_NOMBRE)
            .fecha(DEFAULT_FECHA)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .descripcion(DEFAULT_DESCRIPCION);
        return avistamiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avistamiento createUpdatedEntity(EntityManager em) {
        Avistamiento avistamiento = new Avistamiento()
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .descripcion(UPDATED_DESCRIPCION);
        return avistamiento;
    }

    @BeforeEach
    public void initTest() {
        avistamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvistamiento() throws Exception {
        int databaseSizeBeforeCreate = avistamientoRepository.findAll().size();

        // Create the Avistamiento
        restAvistamientoMockMvc.perform(post("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avistamiento)))
            .andExpect(status().isCreated());

        // Validate the Avistamiento in the database
        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Avistamiento testAvistamiento = avistamientoList.get(avistamientoList.size() - 1);
        assertThat(testAvistamiento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAvistamiento.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testAvistamiento.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testAvistamiento.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testAvistamiento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAvistamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avistamientoRepository.findAll().size();

        // Create the Avistamiento with an existing ID
        avistamiento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvistamientoMockMvc.perform(post("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avistamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Avistamiento in the database
        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = avistamientoRepository.findAll().size();
        // set the field null
        avistamiento.setNombre(null);

        // Create the Avistamiento, which fails.

        restAvistamientoMockMvc.perform(post("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avistamiento)))
            .andExpect(status().isBadRequest());

        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avistamientoRepository.findAll().size();
        // set the field null
        avistamiento.setFecha(null);

        // Create the Avistamiento, which fails.

        restAvistamientoMockMvc.perform(post("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avistamiento)))
            .andExpect(status().isBadRequest());

        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = avistamientoRepository.findAll().size();
        // set the field null
        avistamiento.setLatitud(null);

        // Create the Avistamiento, which fails.

        restAvistamientoMockMvc.perform(post("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avistamiento)))
            .andExpect(status().isBadRequest());

        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = avistamientoRepository.findAll().size();
        // set the field null
        avistamiento.setLongitud(null);

        // Create the Avistamiento, which fails.

        restAvistamientoMockMvc.perform(post("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avistamiento)))
            .andExpect(status().isBadRequest());

        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvistamientos() throws Exception {
        // Initialize the database
        avistamientoRepository.saveAndFlush(avistamiento);

        // Get all the avistamientoList
        restAvistamientoMockMvc.perform(get("/api/avistamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avistamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.toString())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAvistamientosWithEagerRelationshipsIsEnabled() throws Exception {
        AvistamientoResource avistamientoResource = new AvistamientoResource(avistamientoServiceMock);
        when(avistamientoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAvistamientoMockMvc = MockMvcBuilders.standaloneSetup(avistamientoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAvistamientoMockMvc.perform(get("/api/avistamientos?eagerload=true"))
        .andExpect(status().isOk());

        verify(avistamientoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAvistamientosWithEagerRelationshipsIsNotEnabled() throws Exception {
        AvistamientoResource avistamientoResource = new AvistamientoResource(avistamientoServiceMock);
            when(avistamientoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAvistamientoMockMvc = MockMvcBuilders.standaloneSetup(avistamientoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAvistamientoMockMvc.perform(get("/api/avistamientos?eagerload=true"))
        .andExpect(status().isOk());

            verify(avistamientoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAvistamiento() throws Exception {
        // Initialize the database
        avistamientoRepository.saveAndFlush(avistamiento);

        // Get the avistamiento
        restAvistamientoMockMvc.perform(get("/api/avistamientos/{id}", avistamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avistamiento.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.toString()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAvistamiento() throws Exception {
        // Get the avistamiento
        restAvistamientoMockMvc.perform(get("/api/avistamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvistamiento() throws Exception {
        // Initialize the database
        avistamientoService.save(avistamiento);

        int databaseSizeBeforeUpdate = avistamientoRepository.findAll().size();

        // Update the avistamiento
        Avistamiento updatedAvistamiento = avistamientoRepository.findById(avistamiento.getId()).get();
        // Disconnect from session so that the updates on updatedAvistamiento are not directly saved in db
        em.detach(updatedAvistamiento);
        updatedAvistamiento
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .descripcion(UPDATED_DESCRIPCION);

        restAvistamientoMockMvc.perform(put("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvistamiento)))
            .andExpect(status().isOk());

        // Validate the Avistamiento in the database
        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeUpdate);
        Avistamiento testAvistamiento = avistamientoList.get(avistamientoList.size() - 1);
        assertThat(testAvistamiento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAvistamiento.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testAvistamiento.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testAvistamiento.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testAvistamiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingAvistamiento() throws Exception {
        int databaseSizeBeforeUpdate = avistamientoRepository.findAll().size();

        // Create the Avistamiento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvistamientoMockMvc.perform(put("/api/avistamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avistamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Avistamiento in the database
        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvistamiento() throws Exception {
        // Initialize the database
        avistamientoService.save(avistamiento);

        int databaseSizeBeforeDelete = avistamientoRepository.findAll().size();

        // Delete the avistamiento
        restAvistamientoMockMvc.perform(delete("/api/avistamientos/{id}", avistamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Avistamiento> avistamientoList = avistamientoRepository.findAll();
        assertThat(avistamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avistamiento.class);
        Avistamiento avistamiento1 = new Avistamiento();
        avistamiento1.setId(1L);
        Avistamiento avistamiento2 = new Avistamiento();
        avistamiento2.setId(avistamiento1.getId());
        assertThat(avistamiento1).isEqualTo(avistamiento2);
        avistamiento2.setId(2L);
        assertThat(avistamiento1).isNotEqualTo(avistamiento2);
        avistamiento1.setId(null);
        assertThat(avistamiento1).isNotEqualTo(avistamiento2);
    }
}
