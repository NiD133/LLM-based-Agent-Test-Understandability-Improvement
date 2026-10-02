package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.junit.jupiter.api.Test;

public class AppendableJoinerTest_testBuilder extends AbstractLangTest {

    @Test
    void testBuilderCreatesIndependentBuilderInstances() {
        final Builder<Object> firstBuilder = AppendableJoiner.builder();
        final Builder<Object> secondBuilder = AppendableJoiner.builder();

        assertNotSame(firstBuilder, secondBuilder);
    }
}
