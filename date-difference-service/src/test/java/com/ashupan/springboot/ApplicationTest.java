package com.ashupan.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import com.ashupan.springboot.Application;
import com.ashupan.springboot.controller.DateDifferenceController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ApplicationTest {

  @Autowired
  DateDifferenceController dateDifferenceController;
  
  @Test
  public void contextLoads() throws Exception{
      assertThat(dateDifferenceController).isNotNull();
  }
}
