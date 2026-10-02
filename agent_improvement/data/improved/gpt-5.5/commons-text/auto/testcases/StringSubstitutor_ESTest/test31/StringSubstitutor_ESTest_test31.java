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
public class StringSubstitutor_ESTest_test31 extends StringSubstitutor_ESTest_scaffolding {

    private static final int ORIGINAL_OFFSET_ARGUMENT = 807;
    private static final int ORIGINAL_LENGTH_ARGUMENT = 807;
    private static final char DEFAULT_ESCAPE_CHARACTER = '$';

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // A null source is returned before offset and length are validated.
        interpolator.replace((String) null, ORIGINAL_OFFSET_ARGUMENT, ORIGINAL_LENGTH_ARGUMENT);

        assertEquals(DEFAULT_ESCAPE_CHARACTER, interpolator.getEscapeChar());
    }
}
