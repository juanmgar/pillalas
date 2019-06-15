package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.PillalasAlVueloApp;
import com.juanmagarcia.pillalas.domain.Ave;
import com.juanmagarcia.pillalas.repository.AveRepository;
import com.juanmagarcia.pillalas.service.AveService;
import com.juanmagarcia.pillalas.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.juanmagarcia.pillalas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AveResource} REST controller.
 */
@SpringBootTest(classes = PillalasAlVueloApp.class)
public class AveResourceIT {

    private static final String DEFAULT_NOMBRE_COMUN = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMUN = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CIENTIFICO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CIENTIFICO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SONIDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SONIDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SONIDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SONIDO_CONTENT_TYPE = "image/png";

    @Autowired
    private AveRepository aveRepository;

    @Autowired
    private AveService aveService;

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

    private MockMvc restAveMockMvc;

    private Ave ave;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AveResource aveResource = new AveResource(aveService);
        this.restAveMockMvc = MockMvcBuilders.standaloneSetup(aveResource)
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
    public static Ave createEntity(EntityManager em) {
        Ave ave = new Ave()
            .nombreComun(DEFAULT_NOMBRE_COMUN)
            .nombreCientifico(DEFAULT_NOMBRE_CIENTIFICO)
            .descripcion(DEFAULT_DESCRIPCION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .sonido(DEFAULT_SONIDO)
            .sonidoContentType(DEFAULT_SONIDO_CONTENT_TYPE);
        return ave;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ave createUpdatedEntity(EntityManager em) {
        Ave ave = new Ave()
            .nombreComun(UPDATED_NOMBRE_COMUN)
            .nombreCientifico(UPDATED_NOMBRE_CIENTIFICO)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .sonido(UPDATED_SONIDO)
            .sonidoContentType(UPDATED_SONIDO_CONTENT_TYPE);
        return ave;
    }

    @BeforeEach
    public void initTest() {
        ave = createEntity(em);
    }

    @Test
    @Transactional
    public void createAve() throws Exception {
        int databaseSizeBeforeCreate = aveRepository.findAll().size();

        // Create the Ave
        restAveMockMvc.perform(post("/api/aves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ave)))
            .andExpect(status().isCreated());

        // Validate the Ave in the database
        List<Ave> aveList = aveRepository.findAll();
        assertThat(aveList).hasSize(databaseSizeBeforeCreate + 1);
        Ave testAve = aveList.get(aveList.size() - 1);
        assertThat(testAve.getNombreComun()).isEqualTo(DEFAULT_NOMBRE_COMUN);
        assertThat(testAve.getNombreCientifico()).isEqualTo(DEFAULT_NOMBRE_CIENTIFICO);
        assertThat(testAve.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAve.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testAve.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testAve.getSonido()).isEqualTo(DEFAULT_SONIDO);
        assertThat(testAve.getSonidoContentType()).isEqualTo(DEFAULT_SONIDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aveRepository.findAll().size();

        // Create the Ave with an existing ID
        ave.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAveMockMvc.perform(post("/api/aves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ave)))
            .andExpect(status().isBadRequest());

        // Validate the Ave in the database
        List<Ave> aveList = aveRepository.findAll();
        assertThat(aveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreComunIsRequired() throws Exception {
        int databaseSizeBeforeTest = aveRepository.findAll().size();
        // set the field null
        ave.setNombreComun(null);

        // Create the Ave, which fails.

        restAveMockMvc.perform(post("/api/aves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ave)))
            .andExpect(status().isBadRequest());

        List<Ave> aveList = aveRepository.findAll();
        assertThat(aveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreCientificoIsRequired() throws Exception {
        int databaseSizeBeforeTest = aveRepository.findAll().size();
        // set the field null
        ave.setNombreCientifico(null);

        // Create the Ave, which fails.

        restAveMockMvc.perform(post("/api/aves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ave)))
            .andExpect(status().isBadRequest());

        List<Ave> aveList = aveRepository.findAll();
        assertThat(aveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAves() throws Exception {
        // Initialize the database
        aveRepository.saveAndFlush(ave);

        // Get all the aveList
        restAveMockMvc.perform(get("/api/aves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ave.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreComun").value(hasItem(DEFAULT_NOMBRE_COMUN.toString())))
            .andExpect(jsonPath("$.[*].nombreCientifico").value(hasItem(DEFAULT_NOMBRE_CIENTIFICO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].sonidoContentType").value(hasItem(DEFAULT_SONIDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sonido").value(hasItem(Base64Utils.encodeToString(DEFAULT_SONIDO))));
    }
    
    @Test
    @Transactional
    public void getAve() throws Exception {
        // Initialize the database
        aveRepository.saveAndFlush(ave);

        // Get the ave
        restAveMockMvc.perform(get("/api/aves/{id}", ave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ave.getId().intValue()))
            .andExpect(jsonPath("$.nombreComun").value(DEFAULT_NOMBRE_COMUN.toString()))
            .andExpect(jsonPath("$.nombreCientifico").value(DEFAULT_NOMBRE_CIENTIFICO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.sonidoContentType").value(DEFAULT_SONIDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.sonido").value(Base64Utils.encodeToString(DEFAULT_SONIDO)));
    }

    @Test
    @Transactional
    public void getNonExistingAve() throws Exception {
        // Get the ave
        restAveMockMvc.perform(get("/api/aves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAve() throws Exception {
        // Initialize the database
        aveService.save(ave);

        int databaseSizeBeforeUpdate = aveRepository.findAll().size();

        // Update the ave
        Ave updatedAve = aveRepository.findById(ave.getId()).get();
        // Disconnect from session so that the updates on updatedAve are not directly saved in db
        em.detach(updatedAve);
        updatedAve
            .nombreComun(UPDATED_NOMBRE_COMUN)
            .nombreCientifico(UPDATED_NOMBRE_CIENTIFICO)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .sonido(UPDATED_SONIDO)
            .sonidoContentType(UPDATED_SONIDO_CONTENT_TYPE);

        restAveMockMvc.perform(put("/api/aves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAve)))
            .andExpect(status().isOk());

        // Validate the Ave in the database
        List<Ave> aveList = aveRepository.findAll();
        assertThat(aveList).hasSize(databaseSizeBeforeUpdate);
        Ave testAve = aveList.get(aveList.size() - 1);
        assertThat(testAve.getNombreComun()).isEqualTo(UPDATED_NOMBRE_COMUN);
        assertThat(testAve.getNombreCientifico()).isEqualTo(UPDATED_NOMBRE_CIENTIFICO);
        assertThat(testAve.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAve.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testAve.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testAve.getSonido()).isEqualTo(UPDATED_SONIDO);
        assertThat(testAve.getSonidoContentType()).isEqualTo(UPDATED_SONIDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAve() throws Exception {
        int databaseSizeBeforeUpdate = aveRepository.findAll().size();

        // Create the Ave

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAveMockMvc.perform(put("/api/aves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ave)))
            .andExpect(status().isBadRequest());

        // Validate the Ave in the database
        List<Ave> aveList = aveRepository.findAll();
        assertThat(aveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAve() throws Exception {
        // Initialize the database
        aveService.save(ave);

        int databaseSizeBeforeDelete = aveRepository.findAll().size();

        // Delete the ave
        restAveMockMvc.perform(delete("/api/aves/{id}", ave.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Ave> aveList = aveRepository.findAll();
        assertThat(aveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ave.class);
        Ave ave1 = new Ave();
        ave1.setId(1L);
        Ave ave2 = new Ave();
        ave2.setId(ave1.getId());
        assertThat(ave1).isEqualTo(ave2);
        ave2.setId(2L);
        assertThat(ave1).isNotEqualTo(ave2);
        ave1.setId(null);
        assertThat(ave1).isNotEqualTo(ave2);
    }
}
