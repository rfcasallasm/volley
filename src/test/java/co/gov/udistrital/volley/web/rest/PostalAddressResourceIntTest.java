package co.gov.udistrital.volley.web.rest;

import co.gov.udistrital.volley.VolleyApp;

import co.gov.udistrital.volley.domain.PostalAddress;
import co.gov.udistrital.volley.repository.PostalAddressRepository;
import co.gov.udistrital.volley.service.PostalAddressService;
import co.gov.udistrital.volley.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static co.gov.udistrital.volley.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PostalAddressResource REST controller.
 *
 * @see PostalAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolleyApp.class)
public class PostalAddressResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final Long DEFAULT_HOUSE_NUMBER = 1L;
    private static final Long UPDATED_HOUSE_NUMBER = 2L;

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCE = "BBBBBBBBBB";

    @Autowired
    private PostalAddressRepository postalAddressRepository;

    @Autowired
    private PostalAddressService postalAddressService;

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

    private MockMvc restPostalAddressMockMvc;

    private PostalAddress postalAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PostalAddressResource postalAddressResource = new PostalAddressResource(postalAddressService);
        this.restPostalAddressMockMvc = MockMvcBuilders.standaloneSetup(postalAddressResource)
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
    public static PostalAddress createEntity(EntityManager em) {
        PostalAddress postalAddress = new PostalAddress()
            .street(DEFAULT_STREET)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .zipCode(DEFAULT_ZIP_CODE)
            .residence(DEFAULT_RESIDENCE);
        return postalAddress;
    }

    @Before
    public void initTest() {
        postalAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostalAddress() throws Exception {
        int databaseSizeBeforeCreate = postalAddressRepository.findAll().size();

        // Create the PostalAddress
        restPostalAddressMockMvc.perform(post("/api/postal-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isCreated());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PostalAddress testPostalAddress = postalAddressList.get(postalAddressList.size() - 1);
        assertThat(testPostalAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testPostalAddress.getHouseNumber()).isEqualTo(DEFAULT_HOUSE_NUMBER);
        assertThat(testPostalAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testPostalAddress.getResidence()).isEqualTo(DEFAULT_RESIDENCE);
    }

    @Test
    @Transactional
    public void createPostalAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postalAddressRepository.findAll().size();

        // Create the PostalAddress with an existing ID
        postalAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostalAddressMockMvc.perform(post("/api/postal-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isBadRequest());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = postalAddressRepository.findAll().size();
        // set the field null
        postalAddress.setStreet(null);

        // Create the PostalAddress, which fails.

        restPostalAddressMockMvc.perform(post("/api/postal-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isBadRequest());

        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHouseNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = postalAddressRepository.findAll().size();
        // set the field null
        postalAddress.setHouseNumber(null);

        // Create the PostalAddress, which fails.

        restPostalAddressMockMvc.perform(post("/api/postal-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isBadRequest());

        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostalAddresses() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList
        restPostalAddressMockMvc.perform(get("/api/postal-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postalAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].residence").value(hasItem(DEFAULT_RESIDENCE.toString())));
    }
    
    @Test
    @Transactional
    public void getPostalAddress() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get the postalAddress
        restPostalAddressMockMvc.perform(get("/api/postal-addresses/{id}", postalAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(postalAddress.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER.intValue()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.residence").value(DEFAULT_RESIDENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPostalAddress() throws Exception {
        // Get the postalAddress
        restPostalAddressMockMvc.perform(get("/api/postal-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostalAddress() throws Exception {
        // Initialize the database
        postalAddressService.save(postalAddress);

        int databaseSizeBeforeUpdate = postalAddressRepository.findAll().size();

        // Update the postalAddress
        PostalAddress updatedPostalAddress = postalAddressRepository.findById(postalAddress.getId()).get();
        // Disconnect from session so that the updates on updatedPostalAddress are not directly saved in db
        em.detach(updatedPostalAddress);
        updatedPostalAddress
            .street(UPDATED_STREET)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .zipCode(UPDATED_ZIP_CODE)
            .residence(UPDATED_RESIDENCE);

        restPostalAddressMockMvc.perform(put("/api/postal-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPostalAddress)))
            .andExpect(status().isOk());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeUpdate);
        PostalAddress testPostalAddress = postalAddressList.get(postalAddressList.size() - 1);
        assertThat(testPostalAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testPostalAddress.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
        assertThat(testPostalAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testPostalAddress.getResidence()).isEqualTo(UPDATED_RESIDENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingPostalAddress() throws Exception {
        int databaseSizeBeforeUpdate = postalAddressRepository.findAll().size();

        // Create the PostalAddress

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostalAddressMockMvc.perform(put("/api/postal-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isBadRequest());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostalAddress() throws Exception {
        // Initialize the database
        postalAddressService.save(postalAddress);

        int databaseSizeBeforeDelete = postalAddressRepository.findAll().size();

        // Delete the postalAddress
        restPostalAddressMockMvc.perform(delete("/api/postal-addresses/{id}", postalAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostalAddress.class);
        PostalAddress postalAddress1 = new PostalAddress();
        postalAddress1.setId(1L);
        PostalAddress postalAddress2 = new PostalAddress();
        postalAddress2.setId(postalAddress1.getId());
        assertThat(postalAddress1).isEqualTo(postalAddress2);
        postalAddress2.setId(2L);
        assertThat(postalAddress1).isNotEqualTo(postalAddress2);
        postalAddress1.setId(null);
        assertThat(postalAddress1).isNotEqualTo(postalAddress2);
    }
}
