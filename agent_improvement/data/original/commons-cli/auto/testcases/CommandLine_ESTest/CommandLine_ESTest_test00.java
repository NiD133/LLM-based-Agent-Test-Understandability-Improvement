package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test00 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        CommandLine commandLine0 = new CommandLine();
        Option option0 = new Option("D", "D", true, "D");
        Class<Option> class0 = Option.class;
        option0.setType(class0);
        commandLine0.addOption(option0);
        option0.processValue("D");
        Supplier<Class<Option>[]> supplier0 = (Supplier<Class<Option>[]>) mock(Supplier.class, new ViolatedAssumptionAnswer());
        try {
            commandLine0.getParsedOptionValues(option0, supplier0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // java.lang.ClassCastException: Cannot cast java.lang.String to org.apache.commons.cli.Option
            //
            verifyException("org.apache.commons.cli.ParseException", e);
        }
    }
}
