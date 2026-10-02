package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;

public class OptionFormatterTest_testCli343Part1 {

    @Test
    void testCli343Part1() {
        assertBuilderWithoutOptionNameCannotBuild();
        assertBuilderWithoutOptionNameCannotGet();
    }

    private void assertBuilderWithoutOptionNameCannotBuild() {
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).build());
    }

    private void assertBuilderWithoutOptionNameCannotGet() {
        assertThrows(IllegalStateException.class, () -> Option.builder().required(false).get());
    }
}
