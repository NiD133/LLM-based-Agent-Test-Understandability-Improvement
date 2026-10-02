package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test33 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * A default StrSubstitutor should tolerate a null char[] source passed to
     * replace(...) and keep its default escape character ('$').
     */
    @Test(timeout = 4000)
    public void replaceWithNullCharArrayKeepsDefaultEscapeChar() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        substitutor.replace((char[]) null);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
