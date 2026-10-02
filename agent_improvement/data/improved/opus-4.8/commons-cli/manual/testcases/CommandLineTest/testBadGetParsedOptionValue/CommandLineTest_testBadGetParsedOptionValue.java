package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CommandLine#getParsedOptionValue(String)} reports type-conversion
 * failures by throwing a {@link ParseException} that wraps the original cause.
 */
public class CommandLineTest_testBadGetParsedOptionValue {

    /** Enum target for the "-c" converter; note it has no "BAR" constant, so Count.valueOf("BAR") fails. */
    private enum Count {
        ONE, TWO, THREE
    }

    @Test
    void testBadGetParsedOptionValue() throws Exception {
        // Option "-i" expects a Number; option "-c" uses a converter that maps text to a Count enum.
        final Options options = new Options();
        options.addOption(Option.builder("i").hasArg().type(Number.class).get());
        options.addOption(Option.builder("c").hasArg().converter(s -> Count.valueOf(s.toUpperCase())).get());

        // Parse arguments whose values are deliberately invalid for their option types:
        //   "foo" is not a number, and "bar" is not a Count enum constant.
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] { "-i", "foo", "-c", "bar" });

        // Converting "foo" to a Number fails: ParseException must wrap a NumberFormatException.
        final ParseException numberFailure = assertThrows(ParseException.class, () -> cmd.getParsedOptionValue("i"));
        assertEquals(NumberFormatException.class, numberFailure.getCause().getClass());

        // Converting "bar" to a Count fails: ParseException must wrap an IllegalArgumentException
        // (thrown by Enum.valueOf for an unknown constant).
        final ParseException enumFailure = assertThrows(ParseException.class, () -> cmd.getParsedOptionValue("c"));
        assertEquals(IllegalArgumentException.class, enumFailure.getCause().getClass());
    }
}
