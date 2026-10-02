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
public class OptionsTest_testAddOptions2X {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testAddOptions2X() {
        final Options options = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(Option.builder("a").get());
        optionGroup1.addOption(Option.builder("b").get());
        options.addOptionGroup(optionGroup1);
        options.addOption(Option.builder("X").get());
        options.addOption(Option.builder("y").get());
        assertThrows(IllegalArgumentException.class, () -> options.addOptions(options));
    }
}
