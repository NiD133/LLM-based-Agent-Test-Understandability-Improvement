package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.cli.DefaultParser.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class DefaultParserTest_testDeprecated extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testDeprecated() throws ParseException {
        final Set<Option> handler = new HashSet<>();
        parser = DefaultParser.builder().setDeprecatedHandler(handler::add).build();
        final Option opt1 = Option.builder().option("d1").deprecated().get();
        // @formatter:off
        final Option opt2 = Option.builder().option("d2").deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("1.0").setDescription("Do this instead.").get()).get();
        // @formatter:on
        final Option opt3 = Option.builder().option("a").get();
        // @formatter:off
        final CommandLine cl = parser.parse(new Options().addOption(opt1).addOption(opt2).addOption(opt3), new String[] { "-d1", "-d2", "-a" });
        // @formatter:on
        // Trigger handler:
        assertTrue(cl.hasOption(opt1.getOpt()));
        assertTrue(cl.hasOption(opt2.getOpt()));
        assertTrue(cl.hasOption(opt3.getOpt()));
        // Assert handler was triggered
        assertTrue(handler.contains(opt1));
        assertTrue(handler.contains(opt2));
        assertFalse(handler.contains(opt3));
    }
}
