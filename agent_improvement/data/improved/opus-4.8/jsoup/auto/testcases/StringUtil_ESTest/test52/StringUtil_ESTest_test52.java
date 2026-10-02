package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test52 extends StringUtil_ESTest_scaffolding {

    /**
     * StringJoiner.append should return the same joiner instance, allowing fluent
     * chaining, even when given a null separator and a null value to append.
     */
    @Test(timeout = 4000)
    public void appendReturnsSameJoinerInstance() throws Throwable {
        StringUtil.StringJoiner joiner = new StringUtil.StringJoiner((String) null);

        StringUtil.StringJoiner returnedJoiner = joiner.append((Object) null);

        assertSame(joiner, returnedJoiner);
    }
}
