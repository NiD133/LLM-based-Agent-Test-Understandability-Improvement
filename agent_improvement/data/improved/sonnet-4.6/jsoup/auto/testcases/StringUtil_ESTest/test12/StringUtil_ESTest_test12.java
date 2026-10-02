package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.net.URL;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test12 extends StringUtil_ESTest_scaffolding {

    /**
     * When the base URL is null and the relative URL begins with '?', StringUtil.resolve
     * must dereference base to obtain its path (see the query-string workaround in the
     * implementation). Passing null as the base therefore causes a NullPointerException.
     */
    @Test(timeout = 4000)
    public void test_resolveWithNullBaseAndQueryOnlyRelUrl_throwsNullPointerException() throws Throwable {
        try {
            StringUtil.resolve((URL) null, "?3q");
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.evosuite.runtime.mock.java.net.MockURL", e);
        }
    }
}
