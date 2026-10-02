package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Regression test for CLI-343.
 *
 * <p>An {@link Option} must declare at least one of a short opt or a long opt. Building an option
 * that only sets a description leaves it without any opt name, so both terminal builder methods
 * ({@code build()} and {@code get()}) are expected to reject it with an
 * {@link IllegalStateException}.</p>
 */
public class OptionFormatterTest_testCli343Part2 {

    @Test
    void testCli343Part2() {
        // An option with only a description (no opt and no long opt) is invalid.
        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").build());
        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").get());
    }
}
