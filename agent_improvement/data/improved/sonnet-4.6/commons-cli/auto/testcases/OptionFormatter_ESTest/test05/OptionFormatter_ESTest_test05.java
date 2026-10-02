package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test05 extends OptionFormatter_ESTest_scaffolding {

    /**
     * An option with no short name and no description but requiring an argument
     * should produce a syntax string of just the argument placeholder " <arg>"
     * when rendered as a required option.
     */
    @Test(timeout = 4000)
    public void test_syntaxOption_noOptName_withArg_rendersArgPlaceholderOnly() throws Throwable {
        // Option with null short name, hasArg=true, null description
        Option optionWithNoName = new Option(null, true, null);
        OptionFormatter formatter = OptionFormatter.from(optionWithNoName);

        // Required=true: no opt prefix is emitted (opt is null), only the default arg placeholder
        String syntaxOption = formatter.toSyntaxOption(true);

        assertEquals(" <arg>", syntaxOption);
    }
}
