package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testBuilder extends AbstractLangTest {

    @Test
    @DisplayName("builder() returns a new Builder instance on each invocation, not a shared reference")
    void testBuilder() {
        Builder<?> firstBuilder = AppendableJoiner.builder();
        Builder<?> secondBuilder = AppendableJoiner.builder();

        assertNotSame(firstBuilder, secondBuilder);
    }
}
