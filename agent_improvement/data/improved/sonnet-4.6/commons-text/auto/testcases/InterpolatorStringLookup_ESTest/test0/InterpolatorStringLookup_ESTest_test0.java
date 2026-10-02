package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test0 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * When the default StringLookup is null and the key contains no recognized prefix,
     * lookup() should return null because there is no fallback resolver.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Construct with a null default lookup — no fallback resolver is registered
        InterpolatorStringLookup lookupWithNoDefault = new InterpolatorStringLookup((StringLookup) null);

        // Key has no colon-delimited prefix, so no prefix-specific lookup is tried;
        // the null default means there is nothing to delegate to either
        String result = lookupWithNoDefault.lookup("|Hn2uFFA-fI");

        assertNull(result);
    }
}
