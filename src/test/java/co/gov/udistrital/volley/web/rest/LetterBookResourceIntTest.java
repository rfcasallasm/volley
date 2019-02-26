package co.gov.udistrital.volley.web.rest;

import co.gov.udistrital.volley.VolleyApp;

import co.gov.udistrital.volley.domain.LetterBook;
import co.gov.udistrital.volley.repository.LetterBookRepository;
import co.gov.udistrital.volley.service.LetterBookService;
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
 * Test class for the LetterBookResource REST controller.
 *
 * @see LetterBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolleyApp.class)
public class LetterBookResourceIntTest {

    private static final Long DEFAULT_INCOMING_MAIL_NUMBER = 1L;
    private static final Long UPDATED_INCOMING_MAIL_NUMBER = 2L;

    private static final LocalDate DEFAULT_INCOMING_MAIL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INCOMING_MAIL_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LetterBookRepository letterBookRepository;

    @Autowired
    private LetterBookService letterBookService;

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

    private MockMvc restLetterBookMockMvc;

    private LetterBook letterBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LetterBookResource letterBookResource = new LetterBookResource(letterBookService);
        this.restLetterBookMockMvc = MockMvcBuilders.standaloneSetup(letterBookResource)
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
    public static LetterBook createEntity(EntityManager em) {
        LetterBook letterBook = new LetterBook()
            .incomingMailNumber(DEFAULT_INCOMING_MAIL_NUMBER)
            .incomingMailDate(DEFAULT_INCOMING_MAIL_DATE);
        return letterBook;
    }

    @Before
    public void initTest() {
        letterBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createLetterBook() throws Exception {
        int databaseSizeBeforeCreate = letterBookRepository.findAll().size();

        // Create the LetterBook
        restLetterBookMockMvc.perform(post("/api/letter-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letterBook)))
            .andExpect(status().isCreated());

        // Validate the LetterBook in the database
        List<LetterBook> letterBookList = letterBookRepository.findAll();
        assertThat(letterBookList).hasSize(databaseSizeBeforeCreate + 1);
        LetterBook testLetterBook = letterBookList.get(letterBookList.size() - 1);
        assertThat(testLetterBook.getIncomingMailNumber()).isEqualTo(DEFAULT_INCOMING_MAIL_NUMBER);
        assertThat(testLetterBook.getIncomingMailDate()).isEqualTo(DEFAULT_INCOMING_MAIL_DATE);
    }

    @Test
    @Transactional
    public void createLetterBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = letterBookRepository.findAll().size();

        // Create the LetterBook with an existing ID
        letterBook.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLetterBookMockMvc.perform(post("/api/letter-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letterBook)))
            .andExpect(status().isBadRequest());

        // Validate the LetterBook in the database
        List<LetterBook> letterBookList = letterBookRepository.findAll();
        assertThat(letterBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIncomingMailNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = letterBookRepository.findAll().size();
        // set the field null
        letterBook.setIncomingMailNumber(null);

        // Create the LetterBook, which fails.

        restLetterBookMockMvc.perform(post("/api/letter-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letterBook)))
            .andExpect(status().isBadRequest());

        List<LetterBook> letterBookList = letterBookRepository.findAll();
        assertThat(letterBookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLetterBooks() throws Exception {
        // Initialize the database
        letterBookRepository.saveAndFlush(letterBook);

        // Get all the letterBookList
        restLetterBookMockMvc.perform(get("/api/letter-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(letterBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].incomingMailNumber").value(hasItem(DEFAULT_INCOMING_MAIL_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].incomingMailDate").value(hasItem(DEFAULT_INCOMING_MAIL_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getLetterBook() throws Exception {
        // Initialize the database
        letterBookRepository.saveAndFlush(letterBook);

        // Get the letterBook
        restLetterBookMockMvc.perform(get("/api/letter-books/{id}", letterBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(letterBook.getId().intValue()))
            .andExpect(jsonPath("$.incomingMailNumber").value(DEFAULT_INCOMING_MAIL_NUMBER.intValue()))
            .andExpect(jsonPath("$.incomingMailDate").value(DEFAULT_INCOMING_MAIL_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLetterBook() throws Exception {
        // Get the letterBook
        restLetterBookMockMvc.perform(get("/api/letter-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLetterBook() throws Exception {
        // Initialize the database
        letterBookService.save(letterBook);

        int databaseSizeBeforeUpdate = letterBookRepository.findAll().size();

        // Update the letterBook
        LetterBook updatedLetterBook = letterBookRepository.findById(letterBook.getId()).get();
        // Disconnect from session so that the updates on updatedLetterBook are not directly saved in db
        em.detach(updatedLetterBook);
        updatedLetterBook
            .incomingMailNumber(UPDATED_INCOMING_MAIL_NUMBER)
            .incomingMailDate(UPDATED_INCOMING_MAIL_DATE);

        restLetterBookMockMvc.perform(put("/api/letter-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLetterBook)))
            .andExpect(status().isOk());

        // Validate the LetterBook in the database
        List<LetterBook> letterBookList = letterBookRepository.findAll();
        assertThat(letterBookList).hasSize(databaseSizeBeforeUpdate);
        LetterBook testLetterBook = letterBookList.get(letterBookList.size() - 1);
        assertThat(testLetterBook.getIncomingMailNumber()).isEqualTo(UPDATED_INCOMING_MAIL_NUMBER);
        assertThat(testLetterBook.getIncomingMailDate()).isEqualTo(UPDATED_INCOMING_MAIL_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLetterBook() throws Exception {
        int databaseSizeBeforeUpdate = letterBookRepository.findAll().size();

        // Create the LetterBook

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLetterBookMockMvc.perform(put("/api/letter-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(letterBook)))
            .andExpect(status().isBadRequest());

        // Validate the LetterBook in the database
        List<LetterBook> letterBookList = letterBookRepository.findAll();
        assertThat(letterBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLetterBook() throws Exception {
        // Initialize the database
        letterBookService.save(letterBook);

        int databaseSizeBeforeDelete = letterBookRepository.findAll().size();

        // Delete the letterBook
        restLetterBookMockMvc.perform(delete("/api/letter-books/{id}", letterBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LetterBook> letterBookList = letterBookRepository.findAll();
        assertThat(letterBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LetterBook.class);
        LetterBook letterBook1 = new LetterBook();
        letterBook1.setId(1L);
        LetterBook letterBook2 = new LetterBook();
        letterBook2.setId(letterBook1.getId());
        assertThat(letterBook1).isEqualTo(letterBook2);
        letterBook2.setId(2L);
        assertThat(letterBook1).isNotEqualTo(letterBook2);
        letterBook1.setId(null);
        assertThat(letterBook1).isNotEqualTo(letterBook2);
    }
}
