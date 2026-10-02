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
public class OptionsTest_testGetOptionsGroups {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testGetOptionsGroups() {
        final Options options = new Options();
        final OptionGroup optionGroup1 = new OptionGroup();
        optionGroup1.addOption(OptionBuilder.create('a'));
        optionGroup1.addOption(OptionBuilder.create('b'));
        final OptionGroup optionGroup2 = new OptionGroup();
        optionGroup2.addOption(OptionBuilder.create('x'));
        optionGroup2.addOption(OptionBuilder.create('y'));
        options.addOptionGroup(optionGroup1);
        options.addOptionGroup(optionGroup2);
        assertNotNull(options.getOptionGroups());
        assertEquals(2, options.getOptionGroups().size());
    }
}
