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
    public void test11() throws Throwable {
        StringBuilder targetBuilder = new StringBuilder("");
        FailableBiConsumer<Appendable, StringBuilder, IOException> noOpAppender = FailableBiConsumer.nop();
        StringBuffer suffixBuffer = new StringBuffer("");
        StringBuilder[] elements = new StringBuilder[7];

        StringBuilder returnedBuilder = AppendableJoiner.joinSB(
                targetBuilder,
                (CharSequence) targetBuilder,
                (CharSequence) suffixBuffer,
                (CharSequence) targetBuilder,
                noOpAppender,
                elements);

        assertSame(targetBuilder, returnedBuilder);
    }
}
