package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

/**
 * Reproduces CLI-343: an {@link Option} must declare at least a short or long option name.
 *
 * <p>Building one with neither name set, regardless of the {@code required} flag, must fail
 * with an {@link IllegalStateException} — and this holds for both terminal builder methods,
 * {@code build()} and {@code get()}.</p>
 */
public class OptionFormatterTest_testCli343Part1 {

    @Test
    void testCli343Part1() {
        // An Option with no opt and no longOpt is invalid: build() must reject it.
        assertThrows(IllegalStateException.class,
                () -> Option.builder().required(false).build());

        // get() is the Supplier-style alias of build() and must reject it the same way.
        assertThrows(IllegalStateException.class,
                () -> Option.builder().required(false).get());
    }
}
