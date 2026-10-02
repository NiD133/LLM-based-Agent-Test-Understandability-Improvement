package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testShortOptionConcatenatedQuoteHandling extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testShortOptionConcatenatedQuoteHandling() throws Exception {
    }
}
