package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Reproduces CLI-343: an {@link Option} built with only a description, but
 * without either a short opt or a long opt, is invalid and must be rejected.
 */
public class OptionFormatterTest_testCli343Part2 {

    /**
     * Building an option that has a description but no opt and no longOpt must
     * fail. Both terminal builder methods, {@code build()} and {@code get()},
     * are expected to signal this by throwing {@link IllegalStateException}.
     */
    @Test
    void testCli343Part2() {
        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").build());
        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").get());
    }
}
