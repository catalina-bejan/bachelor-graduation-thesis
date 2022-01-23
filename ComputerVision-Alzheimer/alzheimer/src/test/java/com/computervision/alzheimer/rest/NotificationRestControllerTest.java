package com.computervision.alzheimer.rest;

import com.computervision.alzheimer.entity.Notification;
import com.computervision.alzheimer.repository.NotificationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class NotificationRestControllerTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        Notification notificationEat = new Notification("forgot to eat", "You forgot to eat", "The pacient forgot to eat");
        Notification notificationDrink = new Notification("forgot to drink", "You forgot to drink", "The pacient forgot to drink");
        Notification notificationMuch = new Notification("eating too much", "You are eating too much", "The patient is eating too much");
        Notification notificationOften = new Notification("eating too often", "Your are eating too often", "The patient is eating too often");

        notificationRepository.save(notificationEat);
        notificationRepository.save(notificationDrink);
        notificationRepository.save(notificationMuch);
        notificationRepository.save(notificationOften);
    }

    @Test
    public void getAllNotifications() throws Exception {
        String uri = "/api/notifications";

        mvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"type\":\"forgot to eat\",\"patientMessage\":\"You forgot to eat\",\"caretakerMessage\":\"The pacient forgot to eat\"}")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getNotificationById() throws Exception {
        String uri = "/api/notifications/4";
        mvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveNotificationTest() throws Exception {
        String uri = "/api/notifications";
        String body = "{" +
                "\"type\": \"notification\"," +
                "\"patientMessage\": \"Just a message\"," +
                "\"caretakerMessage\": \"Caretaker message\""+
        "}";
        mvc.perform(post(uri).content(body).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateNotificationTest() throws Exception {
        String uri = "/api/notifications";
        String body = "{" +
                "\"id\": 2," +
                "\"type\": \"notification2\"," +
                "\"patientMessage\": \"Just a test\"," +
                "\"caretakerMessage\": \"Caretaker test\""+
                "}";
        mvc.perform(post(uri).content(body).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteNotificationTest() throws Exception {
        String uri = "/api/notifications/3";
        mvc.perform(delete(uri))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}