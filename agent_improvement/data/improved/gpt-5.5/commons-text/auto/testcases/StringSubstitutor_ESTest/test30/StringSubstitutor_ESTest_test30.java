package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test30 extends StringSubstitutor_ESTest_scaffolding {

    private static final int REPLACE_START_OFFSET = 7;
    private static final int REPLACE_LENGTH = 662;

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        String interpolatorDescription = interpolator.toString();

        StringLookup resolverReturningClosingBrace = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(resolverReturningClosingBrace).toString();
        doReturn("}").when(resolverReturningClosingBrace).apply(anyString());

        interpolator.setVariableResolver(resolverReturningClosingBrace);
        interpolator.replace(interpolatorDescription, REPLACE_START_OFFSET, REPLACE_LENGTH);

        assertEquals('$', interpolator.getEscapeChar());
    }
}
