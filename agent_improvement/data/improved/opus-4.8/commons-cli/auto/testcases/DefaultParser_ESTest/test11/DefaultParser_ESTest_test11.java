package org.apache.commons.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test11 extends DefaultParser_ESTest_scaffolding {

    /**
     * When the non-option action is {@link DefaultParser.NonOptionAction#IGNORE},
     * an unknown token that looks like an option (it starts with "-") is simply
     * dropped: no exception is raised and the token is not added as an argument.
     *
     * <p>Because the token is ignored rather than stored, {@code handleUnknownToken}
     * never touches the (still null) command line, so the call completes cleanly.</p>
     */
    @Test(timeout = 4000)
    public void handleUnknownToken_withIgnoreAction_silentlyDropsUnknownOption() throws Throwable {
        DefaultParser parser = new DefaultParser();
        parser.nonOptionAction = DefaultParser.NonOptionAction.IGNORE;

        // "-T" is not a recognized option; IGNORE means it is discarded without error.
        parser.handleUnknownToken("-T");
    }
}
