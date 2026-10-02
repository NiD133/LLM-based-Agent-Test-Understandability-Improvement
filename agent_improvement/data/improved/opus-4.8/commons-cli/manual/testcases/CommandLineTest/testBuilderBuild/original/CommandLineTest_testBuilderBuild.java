package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CommandLineTest_testBuilderBuild {

    @Test
    void testBuilderBuild() {
        // @formatter:off
        final CommandLine cmd = CommandLine.builder().addArg("foo").addArg("bar").addOption(Option.builder("T").get()).build();
        // @formatter:on
        assertEquals("foo", cmd.getArgs()[0]);
        assertEquals("bar", cmd.getArgList().get(1));
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
