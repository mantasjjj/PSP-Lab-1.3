package com.psp.controller;

import com.psp.model.User;
import com.psp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(value = UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService service;

    @Test
    void testShowUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Tadas", "Tadiukinas", "862539592", "tadas@gmail.com", "Adreso g. 5", "Password2!"));
        users.add(new User(2, "Jonas", "Jonaitis", "865421345", "jonas@gmail.com", "Adreso g. 16", "Password3!"));

        when(service.findAll()).thenReturn(users);

        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String expected = "[{\n" +
                "        \"userId\": 1,\n" +
                "        \"firstName\": \"Tadas\",\n" +
                "        \"lastName\": \"Tadiukinas\",\n" +
                "        \"phoneNumber\": \"862539592\",\n" +
                "        \"email\": \"tadas@gmail.com\",\n" +
                "        \"address\": \"Adreso g. 5\",\n" +
                "        \"password\": \"Password2!\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"userId\": 2,\n" +
                "        \"firstName\": \"Jonas\",\n" +
                "        \"lastName\": \"Jonaitis\",\n" +
                "        \"phoneNumber\": \"865421345\",\n" +
                "        \"email\": \"jonas@gmail.com\",\n" +
                "        \"address\": \"Adreso g. 16\",\n" +
                "        \"password\": \"Password3!\"\n" +
                "    }]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void testShowById() throws Exception {
        User user = new User(1, "Tadas", "Tadiukinas", "862539592", "tadas@gmail.com", "Adreso g. 5", "Password2!");

        when(service.findById(Mockito.anyInt())).thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String expected = "{\n" +
                "        \"userId\": 1,\n" +
                "            \"firstName\": \"Tadas\",\n" +
                "            \"lastName\": \"Tadiukinas\",\n" +
                "            \"phoneNumber\": \"862539592\",\n" +
                "            \"email\": \"tadas@gmail.com\",\n" +
                "            \"address\": \"Adreso g. 5\",\n" +
                "            \"password\": \"Password2!\"\n" +
                "    }";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void testDeleteUser() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders
                .delete("/users/1");

        MvcResult result = mockMvc.perform(rb).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
