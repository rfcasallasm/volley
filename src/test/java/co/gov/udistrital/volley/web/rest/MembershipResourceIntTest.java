package co.gov.udistrital.volley.web.rest;

import co.gov.udistrital.volley.VolleyApp;

import co.gov.udistrital.volley.domain.Membership;
import co.gov.udistrital.volley.repository.MembershipRepository;
import co.gov.udistrital.volley.service.MembershipService;
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
 * Test class for the MembershipResource REST controller.
 *
 * @see MembershipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolleyApp.class)
public class MembershipResourceIntTest {

    private static final LocalDate DEFAULT_COMMENCEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMENCEMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MembershipService membershipService;

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

    private MockMvc restMembershipMockMvc;

    private Membership membership;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembershipResource membershipResource = new MembershipResource(membershipService);
        this.restMembershipMockMvc = MockMvcBuilders.standaloneSetup(membershipResource)
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
    public static Membership createEntity(EntityManager em) {
        Membership membership = new Membership()
            .commencementDate(DEFAULT_COMMENCEMENT_DATE)
            .fee(DEFAULT_FEE);
        return membership;
    }

    @Before
    public void initTest() {
        membership = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembership() throws Exception {
        int databaseSizeBeforeCreate = membershipRepository.findAll().size();

        // Create the Membership
        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membership)))
            .andExpect(status().isCreated());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate + 1);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getCommencementDate()).isEqualTo(DEFAULT_COMMENCEMENT_DATE);
        assertThat(testMembership.getFee()).isEqualTo(DEFAULT_FEE);
    }

    @Test
    @Transactional
    public void createMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membershipRepository.findAll().size();

        // Create the Membership with an existing ID
        membership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membership)))
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = membershipRepository.findAll().size();
        // set the field null
        membership.setFee(null);

        // Create the Membership, which fails.

        restMembershipMockMvc.perform(post("/api/memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membership)))
            .andExpect(status().isBadRequest());

        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemberships() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get all the membershipList
        restMembershipMockMvc.perform(get("/api/memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membership.getId().intValue())))
            .andExpect(jsonPath("$.[*].commencementDate").value(hasItem(DEFAULT_COMMENCEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMembership() throws Exception {
        // Initialize the database
        membershipRepository.saveAndFlush(membership);

        // Get the membership
        restMembershipMockMvc.perform(get("/api/memberships/{id}", membership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membership.getId().intValue()))
            .andExpect(jsonPath("$.commencementDate").value(DEFAULT_COMMENCEMENT_DATE.toString()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMembership() throws Exception {
        // Get the membership
        restMembershipMockMvc.perform(get("/api/memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembership() throws Exception {
        // Initialize the database
        membershipService.save(membership);

        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Update the membership
        Membership updatedMembership = membershipRepository.findById(membership.getId()).get();
        // Disconnect from session so that the updates on updatedMembership are not directly saved in db
        em.detach(updatedMembership);
        updatedMembership
            .commencementDate(UPDATED_COMMENCEMENT_DATE)
            .fee(UPDATED_FEE);

        restMembershipMockMvc.perform(put("/api/memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembership)))
            .andExpect(status().isOk());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
        Membership testMembership = membershipList.get(membershipList.size() - 1);
        assertThat(testMembership.getCommencementDate()).isEqualTo(UPDATED_COMMENCEMENT_DATE);
        assertThat(testMembership.getFee()).isEqualTo(UPDATED_FEE);
    }

    @Test
    @Transactional
    public void updateNonExistingMembership() throws Exception {
        int databaseSizeBeforeUpdate = membershipRepository.findAll().size();

        // Create the Membership

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipMockMvc.perform(put("/api/memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membership)))
            .andExpect(status().isBadRequest());

        // Validate the Membership in the database
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembership() throws Exception {
        // Initialize the database
        membershipService.save(membership);

        int databaseSizeBeforeDelete = membershipRepository.findAll().size();

        // Delete the membership
        restMembershipMockMvc.perform(delete("/api/memberships/{id}", membership.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Membership> membershipList = membershipRepository.findAll();
        assertThat(membershipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Membership.class);
        Membership membership1 = new Membership();
        membership1.setId(1L);
        Membership membership2 = new Membership();
        membership2.setId(membership1.getId());
        assertThat(membership1).isEqualTo(membership2);
        membership2.setId(2L);
        assertThat(membership1).isNotEqualTo(membership2);
        membership1.setId(null);
        assertThat(membership1).isNotEqualTo(membership2);
    }
}
