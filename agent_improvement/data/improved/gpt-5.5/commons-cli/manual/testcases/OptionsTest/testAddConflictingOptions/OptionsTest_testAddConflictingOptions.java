package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OptionsTest_testAddConflictingOptions {

    @Test
    void testAddConflictingOptions() {
        final Options existingOptions = new Options();
        final OptionGroup existingGroup = new OptionGroup();
        existingGroup.addOption(Option.builder("a").get());
        existingGroup.addOption(Option.builder("b").get());
        existingOptions.addOptionGroup(existingGroup);
        existingOptions.addOption(Option.builder("x").get());
        existingOptions.addOption(Option.builder("y").get());

        final Options incomingOptions = new Options();
        final OptionGroup incomingGroup = new OptionGroup();
        incomingGroup.addOption(Option.builder("x").type(Integer.class).get());
        incomingGroup.addOption(Option.builder("b").type(Integer.class).get());
        incomingOptions.addOptionGroup(incomingGroup);
        incomingOptions.addOption(Option.builder("c").get());

        assertThrows(IllegalArgumentException.class, () -> existingOptions.addOptions(incomingOptions));
    }
}
