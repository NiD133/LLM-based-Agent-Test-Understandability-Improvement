package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testRequiredOptionInGroupShouldNotBeInRequiredList {

    /**
     * When an option is added directly it can be required, but once it is placed in an
     * {@link OptionGroup} the option itself must become optional (only the group as a whole can
     * be required). This test verifies that adding a required option to a group removes it from
     * the {@link Options} required-options list.
     */
    @Test
    void testRequiredOptionInGroupShouldNotBeInRequiredList() {
        final String shortName = "a";

        // A required, standalone option.
        final Option requiredOption = new Option(shortName, "along", false, "Option A");
        requiredOption.setRequired(true);

        // Adding it directly registers it as a required option.
        final Options options = new Options();
        options.addOption(requiredOption);
        assertTrue(options.getRequiredOptions().contains(shortName),
                "A required option added directly should be in the required options list.");

        // Placing the option in a group must demote it to optional.
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(requiredOption);
        options.addOptionGroup(optionGroup);

        assertFalse(options.getOption(shortName).isRequired(),
                "An option belonging to a group should no longer be required.");
        assertFalse(options.getRequiredOptions().contains(shortName),
                "Option in group shouldn't be in required options list.");
    }
}
