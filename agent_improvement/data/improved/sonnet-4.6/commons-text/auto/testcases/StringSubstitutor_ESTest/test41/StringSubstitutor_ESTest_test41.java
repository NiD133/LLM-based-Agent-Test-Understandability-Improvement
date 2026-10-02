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
public class StringSubstitutor_ESTest_test41 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn() processes the '$$' escape sequence (double dollar)
     * by replacing it with a single '$'.
     *
     * Input is constructed as two copies of "}\0\0\0\0$${}":
     *   - "}" followed by four null chars (from a char array) followed by "$${"
     *     (one '$' from the array, one from the appended "${") followed by "}"
     *   - Doubled by self-append: "}    $${}}    $${}
     *
     * After replaceIn() each "$$" escape becomes "$", so the content becomes
     * two copies of "}\0\0\0\0${}".
     * replaceIn() returns true because at least one escape was processed.
     */
    @Test(timeout = 4000)
    public void test41() throws Throwable {
        StringSubstitutor stringSubstitutor0 = new StringSubstitutor();

        // Build "}\0\0\0\0$${}":
        //   char array ['\0','\0','\0','\0','$'] contributes four nulls and one '$'.
        //   Appending the CharSequence "${" contributes a second '$' (and '{').
        //   Appending "}" closes the pattern, giving "$${}".
        char[] charArray0 = new char[5];
        charArray0[4] = '$';
        StringBuilder input = new StringBuilder("}");
        input.append(charArray0);           // "}\0\0\0\0$"
        input.append((CharSequence) "${");  // "}\0\0\0\0$${"
        input.append("}");                  // "}\0\0\0\0$${}

        // append(Object) converts the current value to a String before mutating,
        // so the content is doubled: "}\0\0\0\0$${}}   \0\0\0\0$${}
        input.append((Object) input);

        // replaceIn() unescapes each "$$" to "$" in-place.
        boolean replacementOccurred = stringSubstitutor0.replaceIn(input);

        // Expected: two copies of "}\0\0\0\0${}"
        String fourNulls = new String(new char[4]);
        String expectedHalf = "}" + fourNulls + "${}";
        assertEquals(expectedHalf + expectedHalf, input.toString());
        assertTrue(replacementOccurred);
    }
}
