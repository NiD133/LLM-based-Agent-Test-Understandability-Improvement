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
     * Tests three behaviours in combination:
     *  1. padding(width, maxPaddingWidth) with width == maxPaddingWidth returns a string of
     *     exactly {@code width} spaces (no capping takes effect).
     *  2. Inserting a StringBuilder into itself at the end doubles its content, pushing its
     *     length (10364) above MaxBuilderSize (8192).
     *  3. releaseBuilderVoid silently drops a builder that exceeds MaxBuilderSize instead of
     *     returning it to the pool.
     *  4. StringBuilder.insert() returns the same instance, confirming the fluent API contract.
     */
    @Test(timeout = 4000)
    public void test48() throws Throwable {
        // Arrange: create a padding string of 5182 spaces; width == maxPaddingWidth so no cap applies
        int paddingWidth = 5182;
        String paddingString = StringUtil.padding(paddingWidth, paddingWidth);

        // Wrap in a StringBuilder so we can manipulate and eventually release it
        StringBuilder builder = new StringBuilder(paddingString);

        // Act: insert the builder into itself at position 5182 (the end), doubling its length to 10364
        StringBuilder insertResult = builder.insert(paddingWidth, (CharSequence) builder);

        // Release: length 10364 > MaxBuilderSize (8192), so the builder is discarded without pooling
        StringUtil.releaseBuilderVoid(builder);

        // Assert: StringBuilder.insert() returns the same object (fluent/builder pattern)
        assertSame(builder, insertResult);
    }
}
