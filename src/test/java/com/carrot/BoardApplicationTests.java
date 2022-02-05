package com.carrot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardApplicationTests {

    @Autowired
    private WebApplicationContext ctx;

    @Test
    @DisplayName("스프링 컨텍스트 로딩")
    void contextLoads() {
        assertThat(ctx).isNotEqualTo(null);
    }

}
