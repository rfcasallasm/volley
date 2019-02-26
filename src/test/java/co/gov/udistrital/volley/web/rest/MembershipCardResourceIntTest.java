package co.gov.udistrital.volley.web.rest;

import co.gov.udistrital.volley.VolleyApp;

import co.gov.udistrital.volley.domain.MembershipCard;
import co.gov.udistrital.volley.repository.MembershipCardRepository;
import co.gov.udistrital.volley.service.MembershipCardService;
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
 * Test class for the MembershipCardResource REST controller.
 *
 * @see MembershipCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolleyApp.class)
public class MembershipCardResourceIntTest {

    private static final Long DEFAULT_MEMBERSHIP_NUMBER = 1L;
    private static final Long UPDATED_MEMBERSHIP_NUMBER = 2L;

    private static final LocalDate DEFAULT_COMMENCEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMENCEMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MembershipCardRepository membershipCardRepository;

    @Autowired
    private MembershipCardService membershipCardService;

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

    private MockMvc restMembershipCardMockMvc;

    private MembershipCard membershipCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembershipCardResource membershipCardResource = new MembershipCardResource(membershipCardService);
        this.restMembershipCardMockMvc = MockMvcBuilders.standaloneSetup(membershipCardResource)
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
    public static MembershipCard createEntity(EntityManager em) {
        MembershipCard membershipCard = new MembershipCard()
            .membershipNumber(DEFAULT_MEMBERSHIP_NUMBER)
            .commencementDate(DEFAULT_COMMENCEMENT_DATE)
            .name(DEFAULT_NAME)
            .birthDate(DEFAULT_BIRTH_DATE);
        return membershipCard;
    }

    @Before
    public void initTest() {
        membershipCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembershipCard() throws Exception {
        int databaseSizeBeforeCreate = membershipCardRepository.findAll().size();

        // Create the MembershipCard
        restMembershipCardMockMvc.perform(post("/api/membership-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membershipCard)))
            .andExpect(status().isCreated());

        // Validate the MembershipCard in the database
        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeCreate + 1);
        MembershipCard testMembershipCard = membershipCardList.get(membershipCardList.size() - 1);
        assertThat(testMembershipCard.getMembershipNumber()).isEqualTo(DEFAULT_MEMBERSHIP_NUMBER);
        assertThat(testMembershipCard.getCommencementDate()).isEqualTo(DEFAULT_COMMENCEMENT_DATE);
        assertThat(testMembershipCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMembershipCard.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void createMembershipCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membershipCardRepository.findAll().size();

        // Create the MembershipCard with an existing ID
        membershipCard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipCardMockMvc.perform(post("/api/membership-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membershipCard)))
            .andExpect(status().isBadRequest());

        // Validate the MembershipCard in the database
        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMembershipNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipCardRepository.findAll().size();
        // set the field null
        membershipCard.setMembershipNumber(null);

        // Create the MembershipCard, which fails.

        restMembershipCardMockMvc.perform(post("/api/membership-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membershipCard)))
            .andExpect(status().isBadRequest());

        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipCardRepository.findAll().size();
        // set the field null
        membershipCard.setName(null);

        // Create the MembershipCard, which fails.

        restMembershipCardMockMvc.perform(post("/api/membership-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membershipCard)))
            .andExpect(status().isBadRequest());

        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipCardRepository.findAll().size();
        // set the field null
        membershipCard.setBirthDate(null);

        // Create the MembershipCard, which fails.

        restMembershipCardMockMvc.perform(post("/api/membership-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membershipCard)))
            .andExpect(status().isBadRequest());

        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembershipCards() throws Exception {
        // Initialize the database
        membershipCardRepository.saveAndFlush(membershipCard);

        // Get all the membershipCardList
        restMembershipCardMockMvc.perform(get("/api/membership-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membershipCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].membershipNumber").value(hasItem(DEFAULT_MEMBERSHIP_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].commencementDate").value(hasItem(DEFAULT_COMMENCEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMembershipCard() throws Exception {
        // Initialize the database
        membershipCardRepository.saveAndFlush(membershipCard);

        // Get the membershipCard
        restMembershipCardMockMvc.perform(get("/api/membership-cards/{id}", membershipCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membershipCard.getId().intValue()))
            .andExpect(jsonPath("$.membershipNumber").value(DEFAULT_MEMBERSHIP_NUMBER.intValue()))
            .andExpect(jsonPath("$.commencementDate").value(DEFAULT_COMMENCEMENT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMembershipCard() throws Exception {
        // Get the membershipCard
        restMembershipCardMockMvc.perform(get("/api/membership-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembershipCard() throws Exception {
        // Initialize the database
        membershipCardService.save(membershipCard);

        int databaseSizeBeforeUpdate = membershipCardRepository.findAll().size();

        // Update the membershipCard
        MembershipCard updatedMembershipCard = membershipCardRepository.findById(membershipCard.getId()).get();
        // Disconnect from session so that the updates on updatedMembershipCard are not directly saved in db
        em.detach(updatedMembershipCard);
        updatedMembershipCard
            .membershipNumber(UPDATED_MEMBERSHIP_NUMBER)
            .commencementDate(UPDATED_COMMENCEMENT_DATE)
            .name(UPDATED_NAME)
            .birthDate(UPDATED_BIRTH_DATE);

        restMembershipCardMockMvc.perform(put("/api/membership-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembershipCard)))
            .andExpect(status().isOk());

        // Validate the MembershipCard in the database
        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeUpdate);
        MembershipCard testMembershipCard = membershipCardList.get(membershipCardList.size() - 1);
        assertThat(testMembershipCard.getMembershipNumber()).isEqualTo(UPDATED_MEMBERSHIP_NUMBER);
        assertThat(testMembershipCard.getCommencementDate()).isEqualTo(UPDATED_COMMENCEMENT_DATE);
        assertThat(testMembershipCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMembershipCard.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMembershipCard() throws Exception {
        int databaseSizeBeforeUpdate = membershipCardRepository.findAll().size();

        // Create the MembershipCard

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipCardMockMvc.perform(put("/api/membership-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membershipCard)))
            .andExpect(status().isBadRequest());

        // Validate the MembershipCard in the database
        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembershipCard() throws Exception {
        // Initialize the database
        membershipCardService.save(membershipCard);

        int databaseSizeBeforeDelete = membershipCardRepository.findAll().size();

        // Delete the membershipCard
        restMembershipCardMockMvc.perform(delete("/api/membership-cards/{id}", membershipCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MembershipCard> membershipCardList = membershipCardRepository.findAll();
        assertThat(membershipCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembershipCard.class);
        MembershipCard membershipCard1 = new MembershipCard();
        membershipCard1.setId(1L);
        MembershipCard membershipCard2 = new MembershipCard();
        membershipCard2.setId(membershipCard1.getId());
        assertThat(membershipCard1).isEqualTo(membershipCard2);
        membershipCard2.setId(2L);
        assertThat(membershipCard1).isNotEqualTo(membershipCard2);
        membershipCard1.setId(null);
        assertThat(membershipCard1).isNotEqualTo(membershipCard2);
    }
}
