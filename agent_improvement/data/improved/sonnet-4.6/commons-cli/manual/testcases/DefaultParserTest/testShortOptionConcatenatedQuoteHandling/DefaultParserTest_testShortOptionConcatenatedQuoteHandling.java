package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DefaultParser} for the short-option concatenated quote-handling behaviour
 * inherited from {@link AbstractParserTestCase}.
 *
 * <p>The base class method {@code testShortOptionConcatenatedQuoteHandling} is disabled here
 * because {@code DefaultParser} exercises that behaviour through its own dedicated parameterized
 * test suite (labelled "DEFAULT behavior"). Running the inherited test on top of the parameterized
 * suite would duplicate the coverage without adding value.
 */
public class DefaultParserTest_testShortOptionConcatenatedQuoteHandling extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Disabled because {@link DefaultParser} covers this scenario through its parameterized
     * tests ("DEFAULT behavior"), making a separate inherited test redundant.
     */
    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testShortOptionConcatenatedQuoteHandling() throws Exception {
    }
}
