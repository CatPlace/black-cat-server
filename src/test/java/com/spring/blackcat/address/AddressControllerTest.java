package com.spring.blackcat.address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    AddressController addressController;

    @Autowired
    private MockMvc mock;

    @Test
    @DisplayName("주소 검색")
    void getAddressSearchTest() throws Exception {
        mock.perform(get("/api/v1/addresses/search?query=서울")).andExpect(status().isOk());
    }
}