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
     * Verifies that the deprecated-option handler is invoked exactly for options
     * that are marked deprecated, and not for regular options.
     *
     * The handler is triggered when the parsed CommandLine is queried via hasOption().
     */
    @Test
    void testDeprecated() throws ParseException {
        // Arrange: collect every deprecated Option that the handler fires for
        final Set<Option> triggeredDeprecatedOptions = new HashSet<>();

        parser = DefaultParser.builder()
                .setDeprecatedHandler(triggeredDeprecatedOptions::add)
                .build();

        // A deprecated option with default (minimal) deprecation attributes
        final Option simpleDeprecatedOption = Option.builder()
                .option("d1")
                .deprecated()
                .get();

        // A deprecated option with full deprecation metadata
        final Option richDeprecatedOption = Option.builder()
                .option("d2")
                .deprecated(DeprecatedAttributes.builder()
                        .setForRemoval(true)
                        .setSince("1.0")
                        .setDescription("Do this instead.")
                        .get())
                .get();

        // A regular (non-deprecated) option — the handler must NOT fire for it
        final Option regularOption = Option.builder()
                .option("a")
                .get();

        final Options options = new Options()
                .addOption(simpleDeprecatedOption)
                .addOption(richDeprecatedOption)
                .addOption(regularOption);

        // Act: parse all three options; hasOption() triggers the deprecated handler
        final CommandLine commandLine = parser.parse(
                options,
                new String[]{"-d1", "-d2", "-a"});

        assertTrue(commandLine.hasOption(simpleDeprecatedOption.getOpt()));
        assertTrue(commandLine.hasOption(richDeprecatedOption.getOpt()));
        assertTrue(commandLine.hasOption(regularOption.getOpt()));

        // Assert: handler fired for both deprecated options, but not for the regular one
        assertTrue(triggeredDeprecatedOptions.contains(simpleDeprecatedOption));
        assertTrue(triggeredDeprecatedOptions.contains(richDeprecatedOption));
        assertFalse(triggeredDeprecatedOptions.contains(regularOption));
    }
}
