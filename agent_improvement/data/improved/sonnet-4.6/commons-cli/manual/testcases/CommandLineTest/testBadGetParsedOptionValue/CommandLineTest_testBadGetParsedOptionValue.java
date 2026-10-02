package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandLineTest_testBadGetParsedOptionValue {

    private enum Count { ONE, TWO, THREE }

    @Test
    void testBadGetParsedOptionValue() throws Exception {
        final Options options = new Options();
        options.addOption(Option.builder("i").hasArg().type(Number.class).get());
        options.addOption(Option.builder("c").hasArg().converter(s -> Count.valueOf(s.toUpperCase())).get());

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, new String[] { "-i", "foo", "-c", "bar" });

        // "-i foo" cannot be parsed as a Number: expect ParseException wrapping NumberFormatException
        ParseException exceptionForNumericOption = assertThrows(ParseException.class, () -> cmd.getParsedOptionValue("i"));
        assertEquals(NumberFormatException.class, exceptionForNumericOption.getCause().getClass());

        // "-c bar" cannot be matched to any Count enum constant: expect ParseException wrapping IllegalArgumentException
        ParseException exceptionForEnumOption = assertThrows(ParseException.class, () -> cmd.getParsedOptionValue("c"));
        assertEquals(IllegalArgumentException.class, exceptionForEnumOption.getCause().getClass());
    }
}
