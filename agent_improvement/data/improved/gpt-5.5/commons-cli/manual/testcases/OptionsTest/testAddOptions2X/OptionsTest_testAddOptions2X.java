package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OptionsTest_testAddOptions2X {

    @Test
    void testAddOptions2X() {
        final Options options = new Options();

        final OptionGroup exclusiveOptions = new OptionGroup();
        exclusiveOptions.addOption(Option.builder("a").get());
        exclusiveOptions.addOption(Option.builder("b").get());
        options.addOptionGroup(exclusiveOptions);

        options.addOption(Option.builder("X").get());
        options.addOption(Option.builder("y").get());

        assertThrows(IllegalArgumentException.class, () -> options.addOptions(options));
    }
}
