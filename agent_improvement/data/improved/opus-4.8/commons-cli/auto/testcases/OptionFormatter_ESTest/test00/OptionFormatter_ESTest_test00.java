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

    /**
     * Verifies that {@link OptionFormatter#toOptional(String)} wraps non-empty
     * text in the default optional delimiters ("[" and "]").
     */
    @Test(timeout = 4000)
    public void toOptional_wrapsTextInDefaultOptionalDelimiters() throws Throwable {
        OptionFormatter optionFormatter = OptionFormatter.from((Option) null);

        String wrapped = optionFormatter.toOptional("-");

        assertEquals("[-]", wrapped);
    }
}
