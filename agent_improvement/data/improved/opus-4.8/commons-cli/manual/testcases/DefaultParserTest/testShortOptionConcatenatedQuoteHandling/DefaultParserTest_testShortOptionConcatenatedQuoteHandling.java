package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link DefaultParser} handles a short option whose value is a
 * concatenated, quoted string.
 *
 * <p>This concrete subclass pins the parser used by the inherited test contract
 * to {@link DefaultParser}. The actual assertions for the "DEFAULT behavior"
 * scenario live in the parameterized tests, so the single inherited test method
 * is intentionally disabled here to avoid duplicate coverage.</p>
 */
public class DefaultParserTest_testShortOptionConcatenatedQuoteHandling extends AbstractParserTestCase {

    /**
     * Uses a plain {@link DefaultParser} as the parser under test.
     */
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Disabled: this scenario is exercised by the parameterized tests as the
     * "DEFAULT behavior" case, so this inherited method is left as an empty
     * no-op to prevent redundant execution.
     */
    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testShortOptionConcatenatedQuoteHandling() throws Exception {
    }
}
