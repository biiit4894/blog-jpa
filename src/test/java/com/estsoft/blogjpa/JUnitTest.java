package com.estsoft.blogjpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {
    @DisplayName("1 + 2는 3이다") // 테스트 이름
    @Test
    public void test() {
        int a = 1;
        int b = 2;
        int sum = 3;

        //assertEquals(sum, a + b); //JUnit5
        assertThat(a + b).isEqualTo(sum); // AssertJ
    }

}
