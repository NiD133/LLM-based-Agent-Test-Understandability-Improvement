package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test37 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * A StrSubstitutor created with the no-argument constructor should use the
     * default escape character ('$') and should not preserve escapes by default.
     */
    @Test(timeout = 4000)
    public void defaultConstructorUsesDollarAsEscapeChar() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        substitutor.isPreserveEscapes();

        assertEquals('$', substitutor.getEscapeChar());
    }
}
