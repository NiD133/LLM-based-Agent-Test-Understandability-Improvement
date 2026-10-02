package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DefaultParser#parse} rejects a {@code null} {@link Options} argument.
 */
public class DefaultParserTest_testParseNullOption extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseNullOption() {
        // The Options argument is required: parsing with a null Options must fail fast
        // with a NullPointerException, regardless of the supplied arguments ("-a").
        assertThrows(NullPointerException.class,
                () -> new DefaultParser().parse(null, null, DefaultParser.NonOptionAction.IGNORE, "-a"));
    }
}
