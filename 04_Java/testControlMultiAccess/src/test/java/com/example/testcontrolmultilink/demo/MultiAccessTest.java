package com.example.testcontrolmultilink.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MultiAccessTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void multiAccessTest() {
    for (int i = 0; i < 100; i++) {
      new Thread(()-> {
        try {
          System.out.println(this.mockMvc.perform(get("/")));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
    }
  }
}