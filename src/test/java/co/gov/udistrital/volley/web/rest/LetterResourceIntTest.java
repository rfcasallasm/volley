package co.gov.udistrital.volley.web.rest;

import co.gov.udistrital.volley.VolleyApp;

import co.gov.udistrital.volley.domain.Letter;
import co.gov.udistrital.volley.repository.LetterRepository;
import co.gov.udistrital.volley.service.LetterService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static co.gov.udistrital.volley.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LetterResource REST controller.
 *
 * @see LetterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolleyApp.class)
public class LetterResourceIntTest {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_SEX = false;
    private static final Boolean UPDATED_SEX = true;

    private static final Long DEFAULT_TELEPHONE_NUMBER = 1L;
    private static final Long UPDATED_TELEPHONE_NUMBER = 2L;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private LetterService letterService;

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

    private MockMvc restLetterMockMvc;

    private Letter letter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LetterResource letterResource = new LetterResource(letterService);
        this.restLetterMockMvc = MockMvcBuilders.standaloneSetup(letterResource)
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
    public static Letter createEntity(EntityManager em) {
        Letter letter = new Letter()
            .firstname(DEFAULT_FIRSTNAME)
            .surname(DEFAULT_SURNAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .sex(DEFAULT_SEX)
            .telephoneNumber(DEFAULT_TELEPHONE_NUMBER);
        return letter;
    }

    @Before
    public void initTest() {
        letter = createEntity(em);
    }

    @Test
    @Transactional
    public void createLetter() throws Exception {
        int databaseSizeBeforeCreate = letterRepository.findAll().size();

        // Create the Letter
        restLetterMockMvc.perform(post("/api/letters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letter)))
            .andExpect(status().isCreated());

        // Validate the Letter in the database
        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeCreate + 1);
        Letter testLetter = letterList.get(letterList.size() - 1);
        assertThat(testLetter.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testLetter.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testLetter.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testLetter.isSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testLetter.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createLetterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = letterRepository.findAll().size();

        // Create the Letter with an existing ID
        letter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLetterMockMvc.perform(post("/api/letters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letter)))
            .andExpect(status().isBadRequest());

        // Validate the Letter in the database
        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = letterRepository.findAll().size();
        // set the field null
        letter.setFirstname(null);

        // Create the Letter, which fails.

        restLetterMockMvc.perform(post("/api/letters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letter)))
            .andExpect(status().isBadRequest());

        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = letterRepository.findAll().size();
        // set the field null
        letter.setSurname(null);

        // Create the Letter, which fails.

        restLetterMockMvc.perform(post("/api/letters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letter)))
            .andExpect(status().isBadRequest());

        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = letterRepository.findAll().size();
        // set the field null
        letter.setSex(null);

        // Create the Letter, which fails.

        restLetterMockMvc.perform(post("/api/letters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letter)))
            .andExpect(status().isBadRequest());

        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLetters() throws Exception {
        // Initialize the database
        letterRepository.saveAndFlush(letter);

        // Get all the letterList
        restLetterMockMvc.perform(get("/api/letters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(letter.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.booleanValue())))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER.intValue())));
    }
    
    @Test
    @Transactional
    public void getLetter() throws Exception {
        // Initialize the database
        letterRepository.saveAndFlush(letter);

        // Get the letter
        restLetterMockMvc.perform(get("/api/letters/{id}", letter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(letter.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.booleanValue()))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLetter() throws Exception {
        // Get the letter
        restLetterMockMvc.perform(get("/api/letters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLetter() throws Exception {
        // Initialize the database
        letterService.save(letter);

        int databaseSizeBeforeUpdate = letterRepository.findAll().size();

        // Update the letter
        Letter updatedLetter = letterRepository.findById(letter.getId()).get();
        // Disconnect from session so that the updates on updatedLetter are not directly saved in db
        em.detach(updatedLetter);
        updatedLetter
            .firstname(UPDATED_FIRSTNAME)
            .surname(UPDATED_SURNAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .sex(UPDATED_SEX)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER);

        restLetterMockMvc.perform(put("/api/letters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLetter)))
            .andExpect(status().isOk());

        // Validate the Letter in the database
        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeUpdate);
        Letter testLetter = letterList.get(letterList.size() - 1);
        assertThat(testLetter.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testLetter.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testLetter.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testLetter.isSex()).isEqualTo(UPDATED_SEX);
        assertThat(testLetter.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingLetter() throws Exception {
        int databaseSizeBeforeUpdate = letterRepository.findAll().size();

        // Create the Letter

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLetterMockMvc.perform(put("/api/letters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letter)))
            .andExpect(status().isBadRequest());

        // Validate the Letter in the database
        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLetter() throws Exception {
        // Initialize the database
        letterService.save(letter);

        int databaseSizeBeforeDelete = letterRepository.findAll().size();

        // Delete the letter
        restLetterMockMvc.perform(delete("/api/letters/{id}", letter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Letter> letterList = letterRepository.findAll();
        assertThat(letterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Letter.class);
        Letter letter1 = new Letter();
        letter1.setId(1L);
        Letter letter2 = new Letter();
        letter2.setId(letter1.getId());
        assertThat(letter1).isEqualTo(letter2);
        letter2.setId(2L);
        assertThat(letter1).isNotEqualTo(letter2);
        letter1.setId(null);
        assertThat(letter1).isNotEqualTo(letter2);
    }
}
