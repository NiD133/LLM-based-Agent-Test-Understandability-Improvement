package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test06 extends OptionFormatter_ESTest_scaffolding {

    /**
     * A Builder created from an existing OptionFormatter copies that formatter's
     * settings, but building from it still yields a brand-new OptionFormatter
     * instance rather than returning the original.
     */
    @Test(timeout = 4000)
    public void buildFromCopiedBuilderReturnsNewInstance() throws Throwable {
        Option option = new Option("arKg", "arKg");
        OptionFormatter originalFormatter = OptionFormatter.from(option);

        OptionFormatter.Builder builderFromOriginal = new OptionFormatter.Builder(originalFormatter);
        OptionFormatter rebuiltFormatter = builderFromOriginal.build(option);

        assertNotSame(originalFormatter, rebuiltFormatter);
    }
}
