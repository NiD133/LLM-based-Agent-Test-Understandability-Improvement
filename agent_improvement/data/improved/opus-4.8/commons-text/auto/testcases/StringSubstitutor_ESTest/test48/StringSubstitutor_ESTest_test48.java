package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test48 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies the static {@link StringSubstitutor#replace(Object, Map, String, String)} overload
     * with a custom variable prefix and suffix.
     *
     * <p>The source text "${" contains no variable delimited by the configured prefix and suffix
     * (both set to "}"), so no substitution occurs and the source is returned unchanged.</p>
     */
    @Test(timeout = 4000)
    public void testReplaceWithCustomDelimitersLeavesSourceUnchanged() throws Throwable {
        Map<String, LinkOption> emptyValues = new HashMap<String, LinkOption>();
        String customPrefix = "}";
        String customSuffix = "}";

        String result = StringSubstitutor.replace((Object) "${", emptyValues, customPrefix, customSuffix);

        assertEquals("${", result);
    }
}
