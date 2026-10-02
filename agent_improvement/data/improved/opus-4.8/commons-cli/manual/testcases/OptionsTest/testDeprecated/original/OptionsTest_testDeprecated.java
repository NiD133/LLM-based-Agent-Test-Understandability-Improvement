package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testDeprecated {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testDeprecated() {
        final Options options = new Options();
        options.addOption(Option.builder().option("a").get());
        options.addOption(Option.builder().option("b").deprecated().get());
        options.addOption(Option.builder().option("c").deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("2.0").setDescription("Use X.").get()).get());
        options.addOption(Option.builder().option("d").deprecated().longOpt("longD").hasArgs().get());
        // toString()
        assertTrue(options.getOption("a").toString().startsWith("[ Option a"));
        assertTrue(options.getOption("b").toString().startsWith("[ Option b"));
        assertTrue(options.getOption("c").toString().startsWith("[ Option c"));
        // toDeprecatedString()
        assertFalse(options.getOption("a").toDeprecatedString().startsWith("Option a"));
        assertEquals("Option 'b': Deprecated", options.getOption("b").toDeprecatedString());
        assertEquals("Option 'c': Deprecated for removal since 2.0: Use X.", options.getOption("c").toDeprecatedString());
        assertToStrings(options.getOption("a"));
        assertToStrings(options.getOption("b"));
        assertToStrings(options.getOption("c"));
        assertToStrings(options.getOption("d"));
    }
}
