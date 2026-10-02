package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Regression tests for CLI-343: Option.builder() must have at least one of
 * opt or longOpt set before build()/get() can be called; omitting both should
 * throw IllegalStateException immediately rather than producing a broken Option.
 */
public class OptionFormatterTest_testCli343Part2 {

    @Test
    @DisplayName("Option.builder() without opt/longOpt throws IllegalStateException on build() and get()")
    void testCli343Part2() {
        // build() must reject a builder that has no opt or longOpt set
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").build());

        // get() (Supplier shortcut) must apply the same validation
        assertThrows(IllegalStateException.class, () -> Option.builder().desc("description").get());
    }
}
