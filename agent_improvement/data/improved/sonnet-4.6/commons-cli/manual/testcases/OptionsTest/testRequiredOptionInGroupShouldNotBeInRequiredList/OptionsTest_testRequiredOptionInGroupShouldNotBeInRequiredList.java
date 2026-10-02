package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// tests some deprecated classes
@SuppressWarnings("deprecation")
public class OptionsTest_testRequiredOptionInGroupShouldNotBeInRequiredList {

    @Test
    void testRequiredOptionInGroupShouldNotBeInRequiredList() {
        // Arrange: create a required option and confirm it appears in the required list
        final String key = "a";
        final Option option = new Option(key, "along", false, "Option A");
        option.setRequired(true);

        final Options options = new Options();
        options.addOption(option);
        assertTrue(options.getRequiredOptions().contains(key),
                "A required option added directly should appear in the required options list.");

        // Act: place the same option inside an OptionGroup and register the group.
        // Options inside a group cannot be individually required — the group itself
        // controls requiredness — so addOptionGroup() strips the required flag and
        // removes the option's key from the required list.
        final OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(option);
        options.addOptionGroup(optionGroup);

        // Assert: the option is no longer marked required and is absent from the required list
        assertFalse(options.getOption(key).isRequired(),
                "An option inside a group should have its required flag cleared.");
        assertFalse(options.getRequiredOptions().contains(key),
                "Option in group shouldn't be in required options list.");
    }
}
