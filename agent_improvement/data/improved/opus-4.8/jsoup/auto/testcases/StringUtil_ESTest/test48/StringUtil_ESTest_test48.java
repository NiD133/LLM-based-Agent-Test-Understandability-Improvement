package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test48 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtil#releaseBuilderVoid(StringBuilder)} tolerates an
     * over-sized builder, and that {@link StringBuilder#insert(int, CharSequence)} returns
     * the same builder instance it was invoked on.
     */
    @Test(timeout = 4000)
    public void releaseBuilderVoidAcceptsOversizedBuilder() throws Throwable {
        // Build a 5182-space string, then double its length by inserting it into itself.
        int paddingWidth = 5182;
        String spaces = StringUtil.padding(paddingWidth, paddingWidth);

        StringBuilder builder = new StringBuilder(spaces);
        StringBuilder insertResult = builder.insert(paddingWidth, (CharSequence) builder);

        // insert(...) mutates and returns the very same builder instance.
        assertSame(builder, insertResult);

        // Releasing should not throw even though the builder exceeds the pool's max size.
        StringUtil.releaseBuilderVoid(builder);
    }
}
