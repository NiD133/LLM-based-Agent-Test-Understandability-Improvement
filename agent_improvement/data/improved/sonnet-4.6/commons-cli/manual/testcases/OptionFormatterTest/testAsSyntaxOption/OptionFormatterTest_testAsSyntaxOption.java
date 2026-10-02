package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.apache.commons.cli.Option;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OptionFormatterTest_testAsSyntaxOption {

    static Stream<Arguments> syntaxOptionTestCases() {
        return Stream.of(
            Arguments.of(
                Option.builder().option("o").longOpt("opt").hasArg().get(),
                "[-o <arg>]",
                "optional short opt with default arg name"
            ),
            Arguments.of(
                Option.builder().option("o").longOpt("opt").hasArg().argName("other").get(),
                "[-o <other>]",
                "optional short opt with custom arg name"
            ),
            Arguments.of(
                Option.builder().option("o").longOpt("opt").hasArg().required().argName("other").get(),
                "-o <other>",
                "required short opt with custom arg name"
            ),
            Arguments.of(
                Option.builder().option("o").longOpt("opt").required().argName("other").get(),
                "-o",
                "required short opt without argument"
            ),
            Arguments.of(
                Option.builder().option("o").argName("other").get(),
                "[-o]",
                "optional short opt without argument"
            ),
            Arguments.of(
                Option.builder().longOpt("opt").hasArg().argName("other").get(),
                "[--opt <other>]",
                "optional long opt with custom arg name"
            ),
            Arguments.of(
                Option.builder().longOpt("opt").required().hasArg().argName("other").get(),
                "--opt <other>",
                "required long opt with custom arg name"
            ),
            Arguments.of(
                Option.builder().option("ot").longOpt("opt").hasArg().get(),
                "[-ot <arg>]",
                "optional multi-character short opt with default arg name"
            )
        );
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("syntaxOptionTestCases")
    void testAsSyntaxOption(Option option, String expectedSyntax, String scenarioDescription) {
        OptionFormatter formatter = OptionFormatter.from(option);
        assertEquals(expectedSyntax, formatter.toSyntaxOption(), scenarioDescription + " failed");
    }
}
