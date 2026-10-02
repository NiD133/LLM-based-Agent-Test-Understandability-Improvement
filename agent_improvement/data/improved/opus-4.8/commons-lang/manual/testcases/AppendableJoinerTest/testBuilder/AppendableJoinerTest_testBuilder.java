package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AppendableJoiner#builder()}.
 */
public class AppendableJoinerTest_testBuilder extends AbstractLangTest {

    /**
     * Each call to {@link AppendableJoiner#builder()} must return a brand-new,
     * independent {@link AppendableJoiner.Builder} instance rather than a shared
     * singleton, so two consecutive calls should never yield the same object.
     */
    @Test
    void testBuilder() {
        assertNotSame(AppendableJoiner.builder(), AppendableJoiner.builder(),
            "builder() should create a fresh Builder instance on every call");
    }
}
