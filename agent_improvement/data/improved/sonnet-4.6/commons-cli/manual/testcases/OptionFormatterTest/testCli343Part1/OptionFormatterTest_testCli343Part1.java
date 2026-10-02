package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Regression tests for CLI-343: Option.builder() must have at least one of opt or longOpt set.
 * Building or getting an Option from a builder that has neither throws IllegalStateException.
 */
public class OptionFormatterTest_testCli343Part1 {

    @Test
    @DisplayName("Option.builder() without opt or longOpt throws IllegalStateException on build() and get()")
    void testCli343Part1() {
        // build() must reject a builder that has no opt and no longOpt
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).build());

        // get() (alias for build()) must also reject the same incomplete builder
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).get());
    }
}
