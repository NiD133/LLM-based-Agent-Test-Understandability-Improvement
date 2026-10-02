package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test01 extends Entities_ESTest_scaffolding {

    /**
     * An empty charset name matches neither "US-ASCII" nor the "UTF-" prefix,
     * so {@link Entities.CoreCharset#byName(String)} falls back to the default
     * {@code fallback} core charset.
     */
    @Test(timeout = 4000)
    public void byName_withEmptyCharsetName_returnsFallback() throws Throwable {
        Entities.CoreCharset coreCharset = Entities.CoreCharset.byName("");

        assertEquals(Entities.CoreCharset.fallback, coreCharset);
    }
}
