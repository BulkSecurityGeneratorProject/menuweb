package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MenuwebApp;

import com.mycompany.myapp.domain.Plat;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Tag;
import com.mycompany.myapp.repository.PlatRepository;
import com.mycompany.myapp.service.PlatService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.PlatCriteria;
import com.mycompany.myapp.service.PlatQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.TypePlat;
/**
 * Test class for the PlatResource REST controller.
 *
 * @see PlatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MenuwebApp.class)
public class PlatResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final TypePlat DEFAULT_TYPE = TypePlat.ENTREE;
    private static final TypePlat UPDATED_TYPE = TypePlat.PLAT_UNIQUE;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private PlatService platService;

    @Autowired
    private PlatQueryService platQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlatMockMvc;

    private Plat plat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlatResource platResource = new PlatResource(platService, platQueryService);
        this.restPlatMockMvc = MockMvcBuilders.standaloneSetup(platResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plat createEntity(EntityManager em) {
        Plat plat = new Plat()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return plat;
    }

    @Before
    public void initTest() {
        plat = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlat() throws Exception {
        int databaseSizeBeforeCreate = platRepository.findAll().size();

        // Create the Plat
        restPlatMockMvc.perform(post("/api/plats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plat)))
            .andExpect(status().isCreated());

        // Validate the Plat in the database
        List<Plat> platList = platRepository.findAll();
        assertThat(platList).hasSize(databaseSizeBeforeCreate + 1);
        Plat testPlat = platList.get(platList.size() - 1);
        assertThat(testPlat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlat.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPlat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPlatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = platRepository.findAll().size();

        // Create the Plat with an existing ID
        plat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlatMockMvc.perform(post("/api/plats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plat)))
            .andExpect(status().isBadRequest());

        // Validate the Plat in the database
        List<Plat> platList = platRepository.findAll();
        assertThat(platList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = platRepository.findAll().size();
        // set the field null
        plat.setName(null);

        // Create the Plat, which fails.

        restPlatMockMvc.perform(post("/api/plats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plat)))
            .andExpect(status().isBadRequest());

        List<Plat> platList = platRepository.findAll();
        assertThat(platList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = platRepository.findAll().size();
        // set the field null
        plat.setType(null);

        // Create the Plat, which fails.

        restPlatMockMvc.perform(post("/api/plats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plat)))
            .andExpect(status().isBadRequest());

        List<Plat> platList = platRepository.findAll();
        assertThat(platList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlats() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList
        restPlatMockMvc.perform(get("/api/plats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPlat() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get the plat
        restPlatMockMvc.perform(get("/api/plats/{id}", plat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllPlatsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where name equals to DEFAULT_NAME
        defaultPlatShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the platList where name equals to UPDATED_NAME
        defaultPlatShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlatsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlatShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the platList where name equals to UPDATED_NAME
        defaultPlatShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlatsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where name is not null
        defaultPlatShouldBeFound("name.specified=true");

        // Get all the platList where name is null
        defaultPlatShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlatsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where type equals to DEFAULT_TYPE
        defaultPlatShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the platList where type equals to UPDATED_TYPE
        defaultPlatShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPlatsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPlatShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the platList where type equals to UPDATED_TYPE
        defaultPlatShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPlatsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where type is not null
        defaultPlatShouldBeFound("type.specified=true");

        // Get all the platList where type is null
        defaultPlatShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlatsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where description equals to DEFAULT_DESCRIPTION
        defaultPlatShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the platList where description equals to UPDATED_DESCRIPTION
        defaultPlatShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlatsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPlatShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the platList where description equals to UPDATED_DESCRIPTION
        defaultPlatShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlatsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        platRepository.saveAndFlush(plat);

        // Get all the platList where description is not null
        defaultPlatShouldBeFound("description.specified=true");

        // Get all the platList where description is null
        defaultPlatShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlatsByCreateurIsEqualToSomething() throws Exception {
        // Initialize the database
        User createur = UserResourceIntTest.createEntity(em);
        em.persist(createur);
        em.flush();
        plat.setCreateur(createur);
        platRepository.saveAndFlush(plat);
        Long createurId = createur.getId();

        // Get all the platList where createur equals to createurId
        defaultPlatShouldBeFound("createurId.equals=" + createurId);

        // Get all the platList where createur equals to createurId + 1
        defaultPlatShouldNotBeFound("createurId.equals=" + (createurId + 1));
    }


    @Test
    @Transactional
    public void getAllPlatsByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        Tag tags = TagResourceIntTest.createEntity(em);
        em.persist(tags);
        em.flush();
        plat.addTags(tags);
        platRepository.saveAndFlush(plat);
        Long tagsId = tags.getId();

        // Get all the platList where tags equals to tagsId
        defaultPlatShouldBeFound("tagsId.equals=" + tagsId);

        // Get all the platList where tags equals to tagsId + 1
        defaultPlatShouldNotBeFound("tagsId.equals=" + (tagsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPlatShouldBeFound(String filter) throws Exception {
        restPlatMockMvc.perform(get("/api/plats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPlatShouldNotBeFound(String filter) throws Exception {
        restPlatMockMvc.perform(get("/api/plats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPlat() throws Exception {
        // Get the plat
        restPlatMockMvc.perform(get("/api/plats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlat() throws Exception {
        // Initialize the database
        platService.save(plat);

        int databaseSizeBeforeUpdate = platRepository.findAll().size();

        // Update the plat
        Plat updatedPlat = platRepository.findOne(plat.getId());
        // Disconnect from session so that the updates on updatedPlat are not directly saved in db
        em.detach(updatedPlat);
        updatedPlat
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION);

        restPlatMockMvc.perform(put("/api/plats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlat)))
            .andExpect(status().isOk());

        // Validate the Plat in the database
        List<Plat> platList = platRepository.findAll();
        assertThat(platList).hasSize(databaseSizeBeforeUpdate);
        Plat testPlat = platList.get(platList.size() - 1);
        assertThat(testPlat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPlat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPlat() throws Exception {
        int databaseSizeBeforeUpdate = platRepository.findAll().size();

        // Create the Plat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlatMockMvc.perform(put("/api/plats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plat)))
            .andExpect(status().isCreated());

        // Validate the Plat in the database
        List<Plat> platList = platRepository.findAll();
        assertThat(platList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlat() throws Exception {
        // Initialize the database
        platService.save(plat);

        int databaseSizeBeforeDelete = platRepository.findAll().size();

        // Get the plat
        restPlatMockMvc.perform(delete("/api/plats/{id}", plat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Plat> platList = platRepository.findAll();
        assertThat(platList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plat.class);
        Plat plat1 = new Plat();
        plat1.setId(1L);
        Plat plat2 = new Plat();
        plat2.setId(plat1.getId());
        assertThat(plat1).isEqualTo(plat2);
        plat2.setId(2L);
        assertThat(plat1).isNotEqualTo(plat2);
        plat1.setId(null);
        assertThat(plat1).isNotEqualTo(plat2);
    }
}
