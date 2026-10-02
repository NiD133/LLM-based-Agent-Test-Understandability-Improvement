package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test06 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that the builder retains its maxWidth value after building a TextStyle,
     * and that pad() can be called on the resulting CENTER-aligned style.
     */
    @Test(timeout = 4000)
    public void test_builderRetainsMaxWidthAfterBuild_andPadRunsOnCenterAlignedStyle() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Builder builderWithMaxWidth = builder.setMaxWidth(3173);
        builderWithMaxWidth.setAlignment(TextStyle.Alignment.CENTER);

        TextStyle centeredStyle = builderWithMaxWidth.get();

        // The text matches the toString() representation of the built style;
        // it is shorter than maxWidth=3173, so pad() adds center-alignment spaces.
        centeredStyle.pad(true, "TextStyle{CENTER, l:0, i:0, true, min:0, max:3173}");

        // builder and builderWithMaxWidth are the same object (fluent API returns this),
        // so the original builder reference also reflects the maxWidth that was set.
        assertEquals(3173, builder.getMaxWidth());
    }
}
