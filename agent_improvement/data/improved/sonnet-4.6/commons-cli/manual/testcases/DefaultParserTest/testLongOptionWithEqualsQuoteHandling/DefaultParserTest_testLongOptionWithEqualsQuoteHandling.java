package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DefaultParser} for the {@code testLongOptionWithEqualsQuoteHandling} scenario.
 *
 * <p>The {@link #testLongOptionWithEqualsQuoteHandling()} method is intentionally disabled here
 * because {@link DefaultParser} handles this case through its parameterized tests rather than
 * through the inherited abstract test case.</p>
 */
public class DefaultParserTest_testLongOptionWithEqualsQuoteHandling extends AbstractParserTestCase {

    /**
     * Initialises the parser under test as a plain {@link DefaultParser} before each test method.
     */
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Disabled because this scenario is covered by the parameterized tests as "DEFAULT behavior".
     */
    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testLongOptionWithEqualsQuoteHandling() throws Exception {
    }
}
