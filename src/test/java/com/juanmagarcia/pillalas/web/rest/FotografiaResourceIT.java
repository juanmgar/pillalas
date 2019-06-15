package com.juanmagarcia.pillalas.web.rest;

import com.juanmagarcia.pillalas.PillalasAlVueloApp;
import com.juanmagarcia.pillalas.domain.Fotografia;
import com.juanmagarcia.pillalas.repository.FotografiaRepository;
import com.juanmagarcia.pillalas.service.FotografiaService;
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
 * Integration tests for the {@Link FotografiaResource} REST controller.
 */
@SpringBootTest(classes = PillalasAlVueloApp.class)
public class FotografiaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHERO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHERO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHERO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHERO_CONTENT_TYPE = "image/png";

    @Autowired
    private FotografiaRepository fotografiaRepository;

    @Autowired
    private FotografiaService fotografiaService;

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

    private MockMvc restFotografiaMockMvc;

    private Fotografia fotografia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FotografiaResource fotografiaResource = new FotografiaResource(fotografiaService);
        this.restFotografiaMockMvc = MockMvcBuilders.standaloneSetup(fotografiaResource)
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
    public static Fotografia createEntity(EntityManager em) {
        Fotografia fotografia = new Fotografia()
            .nombre(DEFAULT_NOMBRE)
            .fichero(DEFAULT_FICHERO)
            .ficheroContentType(DEFAULT_FICHERO_CONTENT_TYPE);
        return fotografia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fotografia createUpdatedEntity(EntityManager em) {
        Fotografia fotografia = new Fotografia()
            .nombre(UPDATED_NOMBRE)
            .fichero(UPDATED_FICHERO)
            .ficheroContentType(UPDATED_FICHERO_CONTENT_TYPE);
        return fotografia;
    }

    @BeforeEach
    public void initTest() {
        fotografia = createEntity(em);
    }

    @Test
    @Transactional
    public void createFotografia() throws Exception {
        int databaseSizeBeforeCreate = fotografiaRepository.findAll().size();

        // Create the Fotografia
        restFotografiaMockMvc.perform(post("/api/fotografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotografia)))
            .andExpect(status().isCreated());

        // Validate the Fotografia in the database
        List<Fotografia> fotografiaList = fotografiaRepository.findAll();
        assertThat(fotografiaList).hasSize(databaseSizeBeforeCreate + 1);
        Fotografia testFotografia = fotografiaList.get(fotografiaList.size() - 1);
        assertThat(testFotografia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testFotografia.getFichero()).isEqualTo(DEFAULT_FICHERO);
        assertThat(testFotografia.getFicheroContentType()).isEqualTo(DEFAULT_FICHERO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFotografiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fotografiaRepository.findAll().size();

        // Create the Fotografia with an existing ID
        fotografia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotografiaMockMvc.perform(post("/api/fotografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotografia)))
            .andExpect(status().isBadRequest());

        // Validate the Fotografia in the database
        List<Fotografia> fotografiaList = fotografiaRepository.findAll();
        assertThat(fotografiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFotografias() throws Exception {
        // Initialize the database
        fotografiaRepository.saveAndFlush(fotografia);

        // Get all the fotografiaList
        restFotografiaMockMvc.perform(get("/api/fotografias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotografia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].ficheroContentType").value(hasItem(DEFAULT_FICHERO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichero").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHERO))));
    }
    
    @Test
    @Transactional
    public void getFotografia() throws Exception {
        // Initialize the database
        fotografiaRepository.saveAndFlush(fotografia);

        // Get the fotografia
        restFotografiaMockMvc.perform(get("/api/fotografias/{id}", fotografia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fotografia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.ficheroContentType").value(DEFAULT_FICHERO_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichero").value(Base64Utils.encodeToString(DEFAULT_FICHERO)));
    }

    @Test
    @Transactional
    public void getNonExistingFotografia() throws Exception {
        // Get the fotografia
        restFotografiaMockMvc.perform(get("/api/fotografias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFotografia() throws Exception {
        // Initialize the database
        fotografiaService.save(fotografia);

        int databaseSizeBeforeUpdate = fotografiaRepository.findAll().size();

        // Update the fotografia
        Fotografia updatedFotografia = fotografiaRepository.findById(fotografia.getId()).get();
        // Disconnect from session so that the updates on updatedFotografia are not directly saved in db
        em.detach(updatedFotografia);
        updatedFotografia
            .nombre(UPDATED_NOMBRE)
            .fichero(UPDATED_FICHERO)
            .ficheroContentType(UPDATED_FICHERO_CONTENT_TYPE);

        restFotografiaMockMvc.perform(put("/api/fotografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFotografia)))
            .andExpect(status().isOk());

        // Validate the Fotografia in the database
        List<Fotografia> fotografiaList = fotografiaRepository.findAll();
        assertThat(fotografiaList).hasSize(databaseSizeBeforeUpdate);
        Fotografia testFotografia = fotografiaList.get(fotografiaList.size() - 1);
        assertThat(testFotografia.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testFotografia.getFichero()).isEqualTo(UPDATED_FICHERO);
        assertThat(testFotografia.getFicheroContentType()).isEqualTo(UPDATED_FICHERO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFotografia() throws Exception {
        int databaseSizeBeforeUpdate = fotografiaRepository.findAll().size();

        // Create the Fotografia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotografiaMockMvc.perform(put("/api/fotografias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotografia)))
            .andExpect(status().isBadRequest());

        // Validate the Fotografia in the database
        List<Fotografia> fotografiaList = fotografiaRepository.findAll();
        assertThat(fotografiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFotografia() throws Exception {
        // Initialize the database
        fotografiaService.save(fotografia);

        int databaseSizeBeforeDelete = fotografiaRepository.findAll().size();

        // Delete the fotografia
        restFotografiaMockMvc.perform(delete("/api/fotografias/{id}", fotografia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Fotografia> fotografiaList = fotografiaRepository.findAll();
        assertThat(fotografiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fotografia.class);
        Fotografia fotografia1 = new Fotografia();
        fotografia1.setId(1L);
        Fotografia fotografia2 = new Fotografia();
        fotografia2.setId(fotografia1.getId());
        assertThat(fotografia1).isEqualTo(fotografia2);
        fotografia2.setId(2L);
        assertThat(fotografia1).isNotEqualTo(fotografia2);
        fotografia1.setId(null);
        assertThat(fotografia1).isNotEqualTo(fotografia2);
    }
}
