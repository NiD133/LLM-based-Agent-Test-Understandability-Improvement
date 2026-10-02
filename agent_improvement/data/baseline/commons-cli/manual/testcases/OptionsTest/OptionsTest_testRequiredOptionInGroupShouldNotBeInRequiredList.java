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
public class OptionsTest_testRequiredOptionInGroupShouldNotBeInRequiredList {

    private void assertToStrings(final Option option) {
        // Should never throw.
        // Should return a String, not null.
        assertNotNull(option.toString());
        assertNotNull(option.toDeprecatedString());
    }

    @Test
    void testRequiredOptionInGroupShouldNotBeInRequiredList() {
        final String key = "a";
        final Option option = new Option(key, "along", false, "Option A");
        option.setRequired(true);
        final Options options = new Options();
        options.addOption(option);
        assertTrue(options.getRequiredOptions().contains(key));
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);
        assertFalse(options.getOption(key).isRequired());
        assertFalse(options.getRequiredOptions().contains(key), "Option in group shouldn't be in required options list.");
    }
}
