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
public class OptionsTest_testGetMatchingOpts {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testGetMatchingOpts() {
        final Options options = new Options();
        OptionBuilder.withLongOpt("version");
        options.addOption(OptionBuilder.create());
        OptionBuilder.withLongOpt("verbose");
        options.addOption(OptionBuilder.create());
        assertTrue(options.getMatchingOptions("foo").isEmpty());
        assertEquals(1, options.getMatchingOptions("version").size());
        assertEquals(2, options.getMatchingOptions("ver").size());
        assertToStrings(options.getOption("version"));
        assertToStrings(options.getOption("verbose"));
    }
}
