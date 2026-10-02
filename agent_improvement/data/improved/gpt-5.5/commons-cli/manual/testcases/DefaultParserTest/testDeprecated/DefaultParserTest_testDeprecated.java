package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testDeprecated extends AbstractParserTestCase {

    private static final String FIRST_DEPRECATED_OPTION = "d1";
    private static final String SECOND_DEPRECATED_OPTION = "d2";
    private static final String ACTIVE_OPTION = "a";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testDeprecated() throws ParseException {
        final Set<Option> deprecatedOptionsSeenByHandler = new HashSet<>();
        parser = DefaultParser.builder().setDeprecatedHandler(deprecatedOptionsSeenByHandler::add).build();

        final Option firstDeprecatedOption = Option.builder().option(FIRST_DEPRECATED_OPTION).deprecated().get();
        final Option secondDeprecatedOption = Option.builder().option(SECOND_DEPRECATED_OPTION).deprecated(
                DeprecatedAttributes.builder().setForRemoval(true).setSince("1.0").setDescription("Do this instead.").get()).get();
        final Option activeOption = Option.builder().option(ACTIVE_OPTION).get();

        final CommandLine commandLine = parser.parse(new Options().addOption(firstDeprecatedOption).addOption(secondDeprecatedOption).addOption(activeOption),
                new String[] { "-d1", "-d2", "-a" });

        assertParsedOptions(commandLine, firstDeprecatedOption, secondDeprecatedOption, activeOption);
        assertDeprecatedHandlerWasCalledOnlyForDeprecatedOptions(deprecatedOptionsSeenByHandler, firstDeprecatedOption, secondDeprecatedOption, activeOption);
    }

    private void assertParsedOptions(final CommandLine commandLine, final Option firstDeprecatedOption, final Option secondDeprecatedOption,
            final Option activeOption) {
        assertTrue(commandLine.hasOption(firstDeprecatedOption.getOpt()));
        assertTrue(commandLine.hasOption(secondDeprecatedOption.getOpt()));
        assertTrue(commandLine.hasOption(activeOption.getOpt()));
    }

    private void assertDeprecatedHandlerWasCalledOnlyForDeprecatedOptions(final Set<Option> deprecatedOptionsSeenByHandler,
            final Option firstDeprecatedOption, final Option secondDeprecatedOption, final Option activeOption) {
        assertTrue(deprecatedOptionsSeenByHandler.contains(firstDeprecatedOption));
        assertTrue(deprecatedOptionsSeenByHandler.contains(secondDeprecatedOption));
        assertFalse(deprecatedOptionsSeenByHandler.contains(activeOption));
    }
}
