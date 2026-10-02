package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testDeprecated extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    /**
     * Verifies that the parser's deprecated-option handler is invoked exactly once for each
     * deprecated option encountered during parsing, and is never invoked for a non-deprecated option.
     */
    @Test
    void testDeprecated() throws ParseException {
        // The handler collects every option that the parser reports as deprecated.
        final Set<Option> deprecatedOptionsSeen = new HashSet<>();
        parser = DefaultParser.builder().setDeprecatedHandler(deprecatedOptionsSeen::add).build();

        // A plainly deprecated option.
        final Option deprecatedOption = Option.builder().option("d1").deprecated().get();

        // A deprecated option that also carries deprecation metadata.
        final DeprecatedAttributes deprecationDetails = DeprecatedAttributes.builder()
                .setForRemoval(true)
                .setSince("1.0")
                .setDescription("Do this instead.")
                .get();
        final Option deprecatedOptionWithDetails = Option.builder().option("d2").deprecated(deprecationDetails).get();

        // A regular, non-deprecated option.
        final Option regularOption = Option.builder().option("a").get();

        final Options options = new Options()
                .addOption(deprecatedOption)
                .addOption(deprecatedOptionWithDetails)
                .addOption(regularOption);

        // Parsing all three options triggers the deprecated handler for the deprecated ones.
        final CommandLine commandLine = parser.parse(options, new String[] {"-d1", "-d2", "-a"});

        // All three options were recognized on the command line.
        assertTrue(commandLine.hasOption(deprecatedOption.getOpt()));
        assertTrue(commandLine.hasOption(deprecatedOptionWithDetails.getOpt()));
        assertTrue(commandLine.hasOption(regularOption.getOpt()));

        // Only the deprecated options were reported to the handler.
        assertTrue(deprecatedOptionsSeen.contains(deprecatedOption));
        assertTrue(deprecatedOptionsSeen.contains(deprecatedOptionWithDetails));
        assertFalse(deprecatedOptionsSeen.contains(regularOption));
    }
}
