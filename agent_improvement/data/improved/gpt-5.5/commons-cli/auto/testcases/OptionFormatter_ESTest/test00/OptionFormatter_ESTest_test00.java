package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test00 extends OptionFormatter_ESTest_scaffolding {

    private static final Option NULL_OPTION = null;
    private static final String TEXT_TO_FORMAT_AS_OPTIONAL = "-";
    private static final String EXPECTED_OPTIONAL_TEXT = "[-]";

    @Test(timeout = 4000)
    public void testToOptionalWrapsTextWithDefaultOptionalDelimiters() throws Throwable {
        OptionFormatter formatter = OptionFormatter.from(NULL_OPTION);

        String optionalText = formatter.toOptional(TEXT_TO_FORMAT_AS_OPTIONAL);

        assertEquals(EXPECTED_OPTIONAL_TEXT, optionalText);
    }
}
