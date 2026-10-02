package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test00 extends OptionFormatter_ESTest_scaffolding {

    // Verifies that toOptional wraps the given text in the default "[" and "]" delimiters,
    // even when the OptionFormatter was created from a null Option.
    @Test(timeout = 4000)
    public void test_toOptional_wrapsTextInDefaultBrackets_whenCreatedFromNullOption() throws Throwable {
        OptionFormatter formatterFromNullOption = OptionFormatter.from((Option) null);
        String result = formatterFromNullOption.toOptional("-");
        assertEquals("[-]", result);
    }
}
