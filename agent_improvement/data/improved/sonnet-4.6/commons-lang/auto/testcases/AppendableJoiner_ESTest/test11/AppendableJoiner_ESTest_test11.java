package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test11 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11_joinSB_returnsTheSameStringBuilderInstance() throws Throwable {
        // Arrange: set up an empty target StringBuilder and supporting parameters
        StringBuilder target = new StringBuilder("");
        FailableBiConsumer<Appendable, StringBuilder, IOException> noOpAppender = FailableBiConsumer.nop();
        StringBuffer emptyBuffer = new StringBuffer("");
        // Use emptyBuffer as prefix and delimiter, target itself as suffix
        StringBuilder[] elementsWithNulls = new StringBuilder[7];

        // Act: join the null-filled array into the target using empty prefix/suffix/delimiter
        StringBuilder result = AppendableJoiner.joinSB(
                target,
                (CharSequence) emptyBuffer,   // prefix
                (CharSequence) target,         // suffix
                (CharSequence) emptyBuffer,   // delimiter
                noOpAppender,
                elementsWithNulls
        );

        // Assert: joinSB must return the exact same StringBuilder instance it received
        assertSame(target, result);
    }
}
