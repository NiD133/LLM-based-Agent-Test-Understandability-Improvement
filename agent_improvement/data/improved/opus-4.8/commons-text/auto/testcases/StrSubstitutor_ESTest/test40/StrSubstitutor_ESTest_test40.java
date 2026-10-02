package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test40 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies the static {@link StrSubstitutor#replace(Object, Map, String, String)} helper.
     * With an empty value map and a prefix/suffix that never delimits a variable in the
     * source text, no substitution can occur, so the call simply returns the source rendered
     * as a (non-null) String. The newly constructed substitutor keeps its default escape char.
     */
    @Test(timeout = 4000)
    public void replaceWithEmptyMapReturnsNonNullResult() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();
        Map<String, Object> emptyValues = new HashMap<String, Object>();
        String variableDelimiter = "৓";

        String result = StrSubstitutor.replace(
                (Object) substitutor, emptyValues, variableDelimiter, variableDelimiter);

        assertNotNull(result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
