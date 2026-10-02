package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testBadGetParsedOptionValue {

    private enum Count {
        ONE
    }

    private static final String INTEGER_OPTION = "i";
    private static final String COUNT_OPTION = "c";

    @Test
    void testBadGetParsedOptionValue() throws Exception {
        final Options options = new Options();
        options.addOption(Option.builder(INTEGER_OPTION).hasArg().type(Number.class).get());
        options.addOption(Option.builder(COUNT_OPTION).hasArg().converter(value -> Count.valueOf(value.toUpperCase())).get());

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, new String[] { "-i", "foo", "-c", "bar" });

        assertParseExceptionCause(NumberFormatException.class, commandLine, INTEGER_OPTION);
        assertParseExceptionCause(IllegalArgumentException.class, commandLine, COUNT_OPTION);
    }

    private static void assertParseExceptionCause(final Class<? extends Exception> expectedCauseType,
            final CommandLine commandLine, final String optionName) {
        final ParseException exception = assertThrows(ParseException.class, () -> commandLine.getParsedOptionValue(optionName));
        assertEquals(expectedCauseType, exception.getCause().getClass());
    }
}
