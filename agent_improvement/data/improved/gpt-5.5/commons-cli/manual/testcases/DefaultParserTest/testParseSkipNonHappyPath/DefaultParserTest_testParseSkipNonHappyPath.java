package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseSkipNonHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseSkipNonHappyPath() throws ParseException {
        final Option firstLetter = Option.builder().option("a").longOpt("first-letter").get();
        final Option secondLetter = Option.builder().option("b").longOpt("second-letter").get();
        final Option thirdLetter = Option.builder().option("c").longOpt("third-letter").get();

        final Options baseOptions = new Options();
        baseOptions.addOption(firstLetter);
        baseOptions.addOption(secondLetter);

        final Options optionsIncludingSkippedToken = new Options();
        optionsIncludingSkippedToken.addOption(firstLetter);
        optionsIncludingSkippedToken.addOption(secondLetter);
        optionsIncludingSkippedToken.addOption(thirdLetter);

        final String[] arguments = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser parser = new DefaultParser();

        final CommandLine parsedWithSkip = parser.parse(baseOptions, null, DefaultParser.NonOptionAction.SKIP, arguments);
        assertEquals(2, parsedWithSkip.getOptions().length);
        assertEquals(4, parsedWithSkip.getArgs().length);

        final UnrecognizedOptionException thrown = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(optionsIncludingSkippedToken, null, DefaultParser.NonOptionAction.THROW, arguments));
        assertTrue(thrown.getMessage().contains("-d"));
    }
}
