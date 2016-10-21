package com.askanything.web;

import com.askanything.web.controllers.IndexAndRegController;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by root on 21.10.16.
 */
public class IndexAndRegControllerTest {
    @Test
    public void index() throws Exception {
        IndexAndRegController controller = new IndexAndRegController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/")).andExpect(view().name("index"));
    }


    @Test
    public void registerOneUser() throws Exception {
        IndexAndRegController controller = new IndexAndRegController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/registration")).andExpect(view().name("registration"));
    }

    @Test
    public void regNewUser() throws Exception {

    }

}