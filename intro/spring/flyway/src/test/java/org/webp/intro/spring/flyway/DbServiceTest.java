package org.webp.intro.spring.flyway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = NONE)
public class DbServiceTest {

    @Autowired
    private DbService service;

    @Test
    public void testDatabase(){

        List<First> firsts = service.getAllFirst();
        List<Second> seconds = service.getAllSecond();

        assertEquals(2, firsts.size());
        assertEquals(1, seconds.size());
    }
}