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

public class CommandLineTest_testBuilderNullArgs {

    @Test
    void testBuilderNullArgs() {
        final CommandLine.Builder builder = CommandLine.builder();
        builder.addArg(null).addArg(null);
        builder.addOption(Option.builder("T").get());
        final CommandLine cmd = builder.build();
        assertEquals(0, cmd.getArgs().length);
        assertEquals("T", cmd.getOptions()[0].getOpt());
    }
}
