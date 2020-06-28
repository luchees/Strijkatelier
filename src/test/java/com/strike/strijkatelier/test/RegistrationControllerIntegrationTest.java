package com.strike.strijkatelier.test;

import com.strike.strijkatelier.*;
import com.strike.strijkatelier.persistence.model.*;
import com.strike.strijkatelier.spring.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;
import org.springframework.web.context.*;

import javax.persistence.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StrijkatelierApplication.class, TestDbConfig.class, TestIntegrationConfig.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class RegistrationControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PersistenceContext
    private EntityManager entityManager;

    private MockMvc mockMvc;
    private String token;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        User user = new User();
        user.setEmail(UUID.randomUUID().toString() + "@example.com");
        user.setPassword(UUID.randomUUID().toString());
        user.setFirstName("First");
        user.setLastName("Last");

        entityManager.persist(user);
        token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationToken.setExpiryDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)));

        entityManager.persist(verificationToken);

        /*
            flush managed entities to the database to populate identifier field
         */
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testRegistrationConfirm() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/registrationConfirm.html?token=" + token));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(model().attribute("message", "Your account verified successfully"));
        resultActions.andExpect(view().name("redirect:/console.html?lang=en"));
    }

    @Test
    public void testRegistrationValidation() throws Exception {

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("firstName", "");
        param.add("lastName", "");
        param.add("email", "");
        param.add("password", "");
        param.add("matchingPassword", "");

        ResultActions resultActions = this.mockMvc.perform(post("/user/registration").params(param));
        resultActions.andExpect(status().is(400));
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.error", is("InvaliduserDto")))
                .andExpect(jsonPath("$.message", containsString("{\"field\":\"lastName\",\"defaultMessage\":\"Length must be greater than 1\"}")));
    }
}