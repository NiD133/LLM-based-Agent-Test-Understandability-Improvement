package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test06 extends OptionGroup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void isSelected_returnsTrue_afterSettingSelectedOption() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();
        Option option = new Option((String) null, "", false, (String) null);
        optionGroup.setSelected(option);
        assertTrue(optionGroup.isSelected());
    }
}
