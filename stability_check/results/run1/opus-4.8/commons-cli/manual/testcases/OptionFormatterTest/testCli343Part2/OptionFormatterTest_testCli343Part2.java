package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Reproduces CLI-343 (part 2): an {@link Option} that carries only a description,
 * but neither a short opt nor a long opt, is invalid and must not be built.
 */
public class OptionFormatterTest_testCli343Part2 {

    @Test
    void testCli343Part2() {
        // An Option needs at least a short or long opt; supplying only a description is illegal.
        // Both terminal builder methods, build() and get(), must reject it.
        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").build());
        assertThrows(IllegalStateException.class,
                () -> Option.builder().desc("description").get());
    }
}
