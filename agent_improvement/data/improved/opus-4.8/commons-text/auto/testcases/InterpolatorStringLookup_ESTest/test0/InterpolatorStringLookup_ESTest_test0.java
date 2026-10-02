package org.apache.commons.text.lookup;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test0 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * When the lookup is built with a {@code null} default {@link StringLookup} and the queried key
     * contains no prefix separator (':'), there is no lookup to delegate to, so {@code lookup}
     * returns {@code null}.
     */
    @Test(timeout = 4000)
    public void lookupWithoutPrefixAndNullDefaultLookupReturnsNull() throws Throwable {
        InterpolatorStringLookup interpolatorWithNullDefault = new InterpolatorStringLookup((StringLookup) null);

        String resolvedValue = interpolatorWithNullDefault.lookup("|Hn2uFFA-fI");

        assertNull(resolvedValue);
    }
}
