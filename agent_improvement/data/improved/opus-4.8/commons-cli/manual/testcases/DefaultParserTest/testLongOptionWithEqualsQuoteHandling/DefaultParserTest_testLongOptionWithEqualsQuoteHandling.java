package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link DefaultParser} handles quotes around the value of a long
 * option written in {@code --name=value} form.
 *
 * <p>The behaviour for this scenario is exercised by the parameterized tests in
 * {@link AbstractParserTestCase} under the "DEFAULT behavior" case, so the
 * dedicated test method here is intentionally disabled to avoid duplicate
 * coverage.</p>
 */
public class DefaultParserTest_testLongOptionWithEqualsQuoteHandling extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Override
    @Test
    @Disabled("Test case handled in the parameterized tests as \"DEFAULT behavior\"")
    void testLongOptionWithEqualsQuoteHandling() throws Exception {
        // Intentionally empty: see class-level Javadoc and the @Disabled reason.
    }
}
