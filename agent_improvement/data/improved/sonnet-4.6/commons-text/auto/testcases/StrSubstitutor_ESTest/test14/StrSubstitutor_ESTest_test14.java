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
public class StrSubstitutor_ESTest_test14 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_replaceInNullStringBuilder_returnsFalseAndKeepsDefaultEscapeChar() throws Throwable {
        // A mocked lookup is sufficient — its behaviour is irrelevant when the target is null
        StrLookup<Object> mockedLookup = (StrLookup<Object>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrSubstitutor substitutor = new StrSubstitutor(mockedLookup);

        // replaceIn on a null StringBuilder should be a no-op and return false
        boolean wasModified = substitutor.replaceIn((StringBuilder) null);

        assertFalse(wasModified);
        // The default escape character must remain '$' regardless of the lookup used
        assertEquals('$', substitutor.getEscapeChar());
    }
}
