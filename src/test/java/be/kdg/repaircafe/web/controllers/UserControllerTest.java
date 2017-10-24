package be.kdg.repaircafe.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register() throws Exception {
        mockMvc.perform(post("/register.do")
                .param("username", "wouter.deketelaere@kdg.be")
                .param("password", "wouter")
                .param("personResource.firstname", "Wouter")
                .param("personResource.lastname", "Deketelaere")
                .param("roleTypes", "ROLE_CLIENT"))
                .andExpect(status().isOk())
                .andExpect(view().name("usercreated"));
    }

}