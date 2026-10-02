package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test00 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * A StringSubstitutor created with the default constructor should expose the
     * default escape character '$', and calling replace on a char[] sub-range
     * should not change that default.
     */
    @Test(timeout = 4000)
    public void defaultEscapeCharIsDollarSign() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        char[] source = new char[9];
        int offset = 1;
        int length = 1;
        substitutor.replace(source, offset, length);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
