package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseNullOption extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseNullOption() throws ParseException {
        // A null Options instance is rejected before any command-line token is parsed.
        assertThrows(NullPointerException.class, () -> new DefaultParser().parse(null, null, DefaultParser.NonOptionAction.IGNORE, "-a"));
    }
}
