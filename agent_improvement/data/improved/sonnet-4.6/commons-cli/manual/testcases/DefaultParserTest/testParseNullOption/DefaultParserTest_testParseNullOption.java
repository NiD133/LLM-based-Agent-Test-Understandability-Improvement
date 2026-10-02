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

    /**
     * Passing a null Options container to parse() must throw NullPointerException immediately,
     * because DefaultParser uses Objects.requireNonNull on the options parameter before touching
     * any command-line arguments.
     */
    @Test
    void testParseNullOption() throws ParseException {
        assertThrows(NullPointerException.class,
                () -> new DefaultParser().parse(null, null, DefaultParser.NonOptionAction.IGNORE, "-a"));
    }
}
