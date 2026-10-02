package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testParseIgnoreNonHappyPath extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testParseIgnoreNonHappyPath() throws ParseException {
        final Option a = Option.builder().option("a").longOpt("first-letter").get();
        final Option b = Option.builder().option("b").longOpt("second-letter").get();
        final Option c = Option.builder().option("c").longOpt("third-letter").get();

        final Options optionsWithoutThirdLetter = new Options();
        optionsWithoutThirdLetter.addOption(a);
        optionsWithoutThirdLetter.addOption(b);

        final Options optionsWithThirdLetter = new Options();
        optionsWithThirdLetter.addOption(a);
        optionsWithThirdLetter.addOption(b);
        optionsWithThirdLetter.addOption(c);

        // -d is intentionally undefined so the same tokens can exercise IGNORE and THROW.
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser parser = new DefaultParser();

        final CommandLine ignoredRogueOption = parser.parse(optionsWithoutThirdLetter, null, DefaultParser.NonOptionAction.IGNORE, args);

        assertEquals(2, ignoredRogueOption.getOptions().length);
        assertEquals(2, ignoredRogueOption.getArgs().length);

        final UnrecognizedOptionException exception = assertThrows(UnrecognizedOptionException.class,
                () -> parser.parse(optionsWithThirdLetter, null, DefaultParser.NonOptionAction.THROW, args));
        assertTrue(exception.getMessage().contains("-d"));
    }
}
