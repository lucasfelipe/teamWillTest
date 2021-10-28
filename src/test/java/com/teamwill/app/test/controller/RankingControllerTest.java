package com.teamwill.app.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamwill.app.test.controller.dto.RankingDTO;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RankingControllerTest {

    @Autowired
    private RankingController controller;

    private MockMvc mvc;
    private JacksonTester<RankingDTO> json;
    private JacksonTester<List<RankingDTO>> jsonList;


    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    @WithMockUser(username = "teamwill", roles = {"ADMIN"})
    @SneakyThrows
    void testGivenRankingDTOWhenCreateThenReturnsCreatedAndLocation() {
        // GIVEN
        val dto = RankingDTO.builder()
                .name("Andreas")
                .rank(100)
                .build();
        val location = API.RANKINGS + "/" + 11;

        // WHEN
        MockHttpServletResponse response = mvc.perform(
                post(API.RANKINGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.write(dto).getJson()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();


        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getHeaders(HttpHeaders.LOCATION).get(0)).isEqualTo(location);
    }

    @Test
    @SneakyThrows
    @WithAnonymousUser
    void testGivenAnonymousUserWhenListAllThenReturnsOkWithList() {
        //GIVEN

        //WHEN
        MockHttpServletResponse response = mvc.perform(get(API.RANKINGS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // THEN
        List<RankingDTO> all = jsonList.parseObject(response.getContentAsString());
        assertThat(all).isNotNull();
        assertThat(all).element(0).extracting("name").isEqualTo("Janaina");
        assertThat(all).element(4).extracting("name").isEqualTo("Monica");
    }

}
