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
public class OptionFormatter_ESTest_test10 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that toArgName wraps its input in the default angle-bracket delimiters ("<" and ">").
     * A single-space string " " should become "< >".
     */
    @Test(timeout = 4000)
    public void test_toArgName_wrapsInputInDefaultAngleBracketDelimiters() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        String formattedArgName = builder.toArgName(" ");

        assertEquals("< >", formattedArgName);
    }
}
