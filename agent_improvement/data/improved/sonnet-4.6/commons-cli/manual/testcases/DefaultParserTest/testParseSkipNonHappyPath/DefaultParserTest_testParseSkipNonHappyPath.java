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
        // Define three individual options
        final Option optionA = Option.builder().option("a").longOpt("first-letter").get();
        final Option optionB = Option.builder().option("b").longOpt("second-letter").get();
        final Option optionC = Option.builder().option("c").longOpt("third-letter").get();

        // baseOptions recognizes only -a and -b; -c and -d are unknown to it
        final Options baseOptions = new Options();
        baseOptions.addOption(optionA);
        baseOptions.addOption(optionB);

        // specificOptions additionally recognizes -c, but -d remains unknown
        final Options specificOptions = new Options();
        specificOptions.addOption(optionA);
        specificOptions.addOption(optionB);
        specificOptions.addOption(optionC);

        // Input mixes recognized options, an option unknown to baseOptions (-c),
        // a completely unknown flag (-d), and plain positional arguments
        final String[] args = { "-a", "-b", "-c", "-d", "arg1", "arg2" };
        final DefaultParser defaultParser = new DefaultParser();

        // SKIP action: unrecognized tokens are passed through as plain arguments instead of causing an error
        final CommandLine baseCommandLine = defaultParser.parse(
                baseOptions, null, DefaultParser.NonOptionAction.SKIP, args);
        assertEquals(2, baseCommandLine.getOptions().length,
                "Only -a and -b should be recognized as parsed options");
        assertEquals(4, baseCommandLine.getArgs().length,
                "Unrecognized tokens (-c, -d, arg1, arg2) should be collected as positional arguments");

        // THROW action: an unrecognized option flag causes an UnrecognizedOptionException
        final UnrecognizedOptionException exception = assertThrows(
                UnrecognizedOptionException.class,
                () -> defaultParser.parse(specificOptions, null, DefaultParser.NonOptionAction.THROW, args),
                "Parsing with THROW action should fail on the unrecognized -d flag");
        assertTrue(exception.getMessage().contains("-d"),
                "The exception message should identify -d as the unrecognized option");
    }
}
