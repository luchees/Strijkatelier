package com.strike.strijkatelier.test;

import com.strike.strijkatelier.*;
import com.strike.strijkatelier.persistence.dao.*;
import com.strike.strijkatelier.persistence.model.*;
import com.strike.strijkatelier.spring.*;
import io.restassured.*;
import io.restassured.response.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import org.springframework.security.crypto.password.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StrijkatelierApplication.class, TestDbConfig.class, TestIntegrationConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private DeviceMetadataRepository deviceMetadataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JavaMailSender mailSender;

    @Value("${local.server.port}")
    int port;

    //

    private Long userId;

    @Before
    public void init() {
        User user = userRepository.findByEmail("test@test.com");
        if (user == null) {
            user = new User();
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setEnabled(true);
            user = userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode("test"));
            user = userRepository.save(user);
        }

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        userId = user.getId();
    }

    @Test
    public void givenValidLoginRequest_whenNoPreviousKnownDevices_shouldSendLoginNotification() {
        final Response response = given()
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .header("X-Forwarded-For", "88.198.50.103") // Nuremberg
                .formParams("username", "test@test.com", "password", "test")
                .post("/login");

        assertEquals(302, response.statusCode());
        assertEquals("http://localhost:" + port + "/console.html", response.getHeader("Location"));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void givenValidLoginRequest_whenUsingKnownDevice_shouldNotSendLoginNotification() {
        DeviceMetadata existingDeviceMetadata = new DeviceMetadata();
        existingDeviceMetadata.setUserId(userId);
        existingDeviceMetadata.setLastLoggedIn(new Date());
        existingDeviceMetadata.setLocation("Nuremberg");
        existingDeviceMetadata.setDeviceDetails("Chrome 71.0 - Mac OS X 10.14");
        when(deviceMetadataRepository.findByUserId(userId)).thenReturn(Collections.singletonList(existingDeviceMetadata));

        final Response response = given()
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .header("X-Forwarded-For", "88.198.50.103") // Nuremberg
                .formParams("username", "test@test.com", "password", "test")
                .post("/login");

        assertEquals(302, response.statusCode());
        assertEquals("http://localhost:" + port + "/console.html", response.getHeader("Location"));
        verify(mailSender, times(0)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void givenValidLoginRequest_whenUsingNewDevice_shouldSendLoginNotification() {
        DeviceMetadata existingDeviceMetadata = new DeviceMetadata();
        existingDeviceMetadata.setUserId(userId);
        existingDeviceMetadata.setLastLoggedIn(new Date());
        existingDeviceMetadata.setLocation("Nuremberg");
        existingDeviceMetadata.setDeviceDetails("Chrome 71.0 - Mac OS X 10.14");
        when(deviceMetadataRepository.findByUserId(userId)).thenReturn(Collections.singletonList(existingDeviceMetadata));

        final Response response = given()
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Safari/605.1.15")
                .header("X-Forwarded-For", "88.198.50.103") // Nuremberg
                .formParams("username", "test@test.com", "password", "test")
                .post("/login");

        assertEquals(302, response.statusCode());
        assertEquals("http://localhost:" + port + "/console.html", response.getHeader("Location"));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void givenValidLoginRequest_whenUsingKnownDeviceFromDifferentLocation_shouldSendLoginNotification() {
        DeviceMetadata existingDeviceMetadata = new DeviceMetadata();
        existingDeviceMetadata.setUserId(userId);
        existingDeviceMetadata.setLastLoggedIn(new Date());
        existingDeviceMetadata.setLocation("Nuremberg");
        existingDeviceMetadata.setDeviceDetails("Chrome 71.0 - Mac OS X 10.14");
        when(deviceMetadataRepository.findByUserId(userId)).thenReturn(Collections.singletonList(existingDeviceMetadata));

        final Response response = given()
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .header("X-Forwarded-For", "81.47.169.143") // Barcelona
                .formParams("username", "test@test.com", "password", "test")
                .post("/login");

        assertEquals(302, response.statusCode());
        assertEquals("http://localhost:" + port + "/console.html", response.getHeader("Location"));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}
