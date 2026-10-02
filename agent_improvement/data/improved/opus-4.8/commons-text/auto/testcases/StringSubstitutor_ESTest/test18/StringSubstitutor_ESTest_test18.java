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
public class StringSubstitutor_ESTest_test18 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(StringBuffer) can run against a substitutor whose
     * variable resolver is backed by a null value map, using custom prefix/suffix,
     * escape character and value delimiter.
     */
    @Test(timeout = 4000)
    public void replaceInStringBufferWithNullValueMapSubstitutor() throws Throwable {
        // Build the input text from the default interpolator's toString() representation.
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        String interpolatorDescription = interpolator.toString();

        // Substitutor backed by a null value map, with custom "${" prefix, "}" suffix,
        // '$' escape character and ":-" value delimiter.
        StringSubstitutor substitutorWithNullMap =
                new StringSubstitutor((Map<String, LinkOption>) null, "${", ":-", '$', "}");

        StringBuffer buffer = new StringBuffer(interpolatorDescription);

        // replaceIn returns whether the buffer was altered; behaviour is exercised here.
        boolean replaced = substitutorWithNullMap.replaceIn(buffer);

        //  // Unstable assertion: assertEquals(2307, stringBuffer0.length());
        //  // Unstable assertion: assertTrue(boolean0);
    }
}
