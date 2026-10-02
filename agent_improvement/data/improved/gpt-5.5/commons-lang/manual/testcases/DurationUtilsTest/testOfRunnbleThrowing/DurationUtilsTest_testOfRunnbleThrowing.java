package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testOfRunnbleThrowing extends AbstractLangTest {

    @Test
    void testOfRunnbleThrowing() {
        assertThrows(IOException.class, () -> DurationUtils.of(() -> {
            throw new IOException();
        }));
    }
}
