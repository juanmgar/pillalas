package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.PillalasAlVueloApp;
import com.juanmagarcia.pillalas.domain.Observatorio;
import com.juanmagarcia.pillalas.repository.ObservatorioRepository;
import com.juanmagarcia.pillalas.service.ObservatorioService;
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
import java.util.ArrayList;
import java.util.List;

import static com.juanmagarcia.pillalas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ObservatorioResource} REST controller.
 */
@SpringBootTest(classes = PillalasAlVueloApp.class)
public class ObservatorioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private ObservatorioRepository observatorioRepository;

    @Mock
    private ObservatorioRepository observatorioRepositoryMock;

    @Mock
    private ObservatorioService observatorioServiceMock;

    @Autowired
    private ObservatorioService observatorioService;

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

    private MockMvc restObservatorioMockMvc;

    private Observatorio observatorio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObservatorioResource observatorioResource = new ObservatorioResource(observatorioService);
        this.restObservatorioMockMvc = MockMvcBuilders.standaloneSetup(observatorioResource)
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
    public static Observatorio createEntity(EntityManager em) {
        Observatorio observatorio = new Observatorio()
            .nombre(DEFAULT_NOMBRE)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .descripcion(DEFAULT_DESCRIPCION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return observatorio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observatorio createUpdatedEntity(EntityManager em) {
        Observatorio observatorio = new Observatorio()
            .nombre(UPDATED_NOMBRE)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        return observatorio;
    }

    @BeforeEach
    public void initTest() {
        observatorio = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservatorio() throws Exception {
        int databaseSizeBeforeCreate = observatorioRepository.findAll().size();

        // Create the Observatorio
        restObservatorioMockMvc.perform(post("/api/observatorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observatorio)))
            .andExpect(status().isCreated());

        // Validate the Observatorio in the database
        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeCreate + 1);
        Observatorio testObservatorio = observatorioList.get(observatorioList.size() - 1);
        assertThat(testObservatorio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testObservatorio.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testObservatorio.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testObservatorio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testObservatorio.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testObservatorio.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createObservatorioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observatorioRepository.findAll().size();

        // Create the Observatorio with an existing ID
        observatorio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservatorioMockMvc.perform(post("/api/observatorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observatorio)))
            .andExpect(status().isBadRequest());

        // Validate the Observatorio in the database
        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = observatorioRepository.findAll().size();
        // set the field null
        observatorio.setNombre(null);

        // Create the Observatorio, which fails.

        restObservatorioMockMvc.perform(post("/api/observatorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observatorio)))
            .andExpect(status().isBadRequest());

        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = observatorioRepository.findAll().size();
        // set the field null
        observatorio.setLatitud(null);

        // Create the Observatorio, which fails.

        restObservatorioMockMvc.perform(post("/api/observatorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observatorio)))
            .andExpect(status().isBadRequest());

        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = observatorioRepository.findAll().size();
        // set the field null
        observatorio.setLongitud(null);

        // Create the Observatorio, which fails.

        restObservatorioMockMvc.perform(post("/api/observatorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observatorio)))
            .andExpect(status().isBadRequest());

        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllObservatorios() throws Exception {
        // Initialize the database
        observatorioRepository.saveAndFlush(observatorio);

        // Get all the observatorioList
        restObservatorioMockMvc.perform(get("/api/observatorios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observatorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.toString())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllObservatoriosWithEagerRelationshipsIsEnabled() throws Exception {
        ObservatorioResource observatorioResource = new ObservatorioResource(observatorioServiceMock);
        when(observatorioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restObservatorioMockMvc = MockMvcBuilders.standaloneSetup(observatorioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restObservatorioMockMvc.perform(get("/api/observatorios?eagerload=true"))
        .andExpect(status().isOk());

        verify(observatorioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllObservatoriosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ObservatorioResource observatorioResource = new ObservatorioResource(observatorioServiceMock);
            when(observatorioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restObservatorioMockMvc = MockMvcBuilders.standaloneSetup(observatorioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restObservatorioMockMvc.perform(get("/api/observatorios?eagerload=true"))
        .andExpect(status().isOk());

            verify(observatorioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getObservatorio() throws Exception {
        // Initialize the database
        observatorioRepository.saveAndFlush(observatorio);

        // Get the observatorio
        restObservatorioMockMvc.perform(get("/api/observatorios/{id}", observatorio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(observatorio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.toString()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingObservatorio() throws Exception {
        // Get the observatorio
        restObservatorioMockMvc.perform(get("/api/observatorios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservatorio() throws Exception {
        // Initialize the database
        observatorioService.save(observatorio);

        int databaseSizeBeforeUpdate = observatorioRepository.findAll().size();

        // Update the observatorio
        Observatorio updatedObservatorio = observatorioRepository.findById(observatorio.getId()).get();
        // Disconnect from session so that the updates on updatedObservatorio are not directly saved in db
        em.detach(updatedObservatorio);
        updatedObservatorio
            .nombre(UPDATED_NOMBRE)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restObservatorioMockMvc.perform(put("/api/observatorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedObservatorio)))
            .andExpect(status().isOk());

        // Validate the Observatorio in the database
        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeUpdate);
        Observatorio testObservatorio = observatorioList.get(observatorioList.size() - 1);
        assertThat(testObservatorio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testObservatorio.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testObservatorio.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testObservatorio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testObservatorio.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testObservatorio.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingObservatorio() throws Exception {
        int databaseSizeBeforeUpdate = observatorioRepository.findAll().size();

        // Create the Observatorio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservatorioMockMvc.perform(put("/api/observatorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observatorio)))
            .andExpect(status().isBadRequest());

        // Validate the Observatorio in the database
        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObservatorio() throws Exception {
        // Initialize the database
        observatorioService.save(observatorio);

        int databaseSizeBeforeDelete = observatorioRepository.findAll().size();

        // Delete the observatorio
        restObservatorioMockMvc.perform(delete("/api/observatorios/{id}", observatorio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Observatorio> observatorioList = observatorioRepository.findAll();
        assertThat(observatorioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Observatorio.class);
        Observatorio observatorio1 = new Observatorio();
        observatorio1.setId(1L);
        Observatorio observatorio2 = new Observatorio();
        observatorio2.setId(observatorio1.getId());
        assertThat(observatorio1).isEqualTo(observatorio2);
        observatorio2.setId(2L);
        assertThat(observatorio1).isNotEqualTo(observatorio2);
        observatorio1.setId(null);
        assertThat(observatorio1).isNotEqualTo(observatorio2);
    }
}
