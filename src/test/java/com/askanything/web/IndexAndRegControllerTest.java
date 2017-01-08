package com.askanything.web;

import com.askanything.models.DAO.UserDao;
import com.askanything.models.entitys.User;
import com.askanything.web.controllers.IndexAndRegController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
/**
 * Created by VadimOz on 21.10.16.
 */
public class IndexAndRegControllerTest {
    @Autowired
    UserDao userDaoMock;

    @Test
    public void index() throws Exception {
        IndexAndRegController controller = new IndexAndRegController(userDaoMock);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/")).andExpect(view().name("index"));
    }



    @Test
    public void registerPage() throws Exception {
        IndexAndRegController controller = new IndexAndRegController(userDaoMock);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/registration")).andExpect(view().name("registration"));
    }

    @Test
    public void regNewUser() throws Exception {
        User newUser = new User("TestUser","1234","test@email.com","TestFirst","TestLast");
        when(userDaoMock.regNewUser(newUser)).thenReturn(true);
        IndexAndRegController controller = new IndexAndRegController(userDaoMock);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration",newUser)).andExpect(view().name("index"));
    }

}