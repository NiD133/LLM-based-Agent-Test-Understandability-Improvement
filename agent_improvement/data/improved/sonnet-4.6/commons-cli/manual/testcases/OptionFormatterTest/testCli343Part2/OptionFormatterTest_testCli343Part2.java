package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Verifies the fix for CLI-343: Option.builder() must require at least one
 * option key (opt or longOpt) and must throw IllegalStateException when
 * build()/get() is called without one being set.
 */
public class OptionFormatterTest_testCli343Part2 {

    @Test
    void testCli343Part2() {
        // An Option with only a description but no opt/longOpt must be rejected at build time.
        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").build(),
                "build() without opt should throw IllegalStateException");

        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").get(),
                "get() without opt should throw IllegalStateException");
    }
}
