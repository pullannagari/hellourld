package com.cloudflare.hellourld;

import com.cloudflare.hellourld.representation.URLRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*ignoring tests to enable them to run against test db*/

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HelloURLDControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    //@Test
    public void testShortURLNotFound() {
        String res = this.restTemplate.getForObject("http://localhost:" + port + "/url/e",
                String.class);
        assertThat(res).contains("No value present");
    }

    //@Test
    public void testPostShortURL() throws Exception {
        URLRepresentation urlRep = new URLRepresentation();
        urlRep.setOriginalURL("https://google.com");
        this.mockMvc.perform(post("/url/b")).andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(content().string(containsString("")));
    }

    //@Test
    public void testShortURLFound() throws Exception {
        this.mockMvc.perform(post("/url/b")).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("")));
    }

}
