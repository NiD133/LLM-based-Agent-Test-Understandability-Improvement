package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test11 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        final String customVariableBoundary = ".@let_E6[gcD#*{";

        final StrSubstitutor substitutor = new StrSubstitutor();
        final StrSubstitutor sameSubstitutor = substitutor.setVariableSuffix(customVariableBoundary);
        sameSubstitutor.setVariablePrefix(customVariableBoundary);

        final StringBuilder source = new StringBuilder(customVariableBoundary);
        source.append(customVariableBoundary);

        final boolean wasReplaced = substitutor.replaceIn(source);

        assertFalse(wasReplaced);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
