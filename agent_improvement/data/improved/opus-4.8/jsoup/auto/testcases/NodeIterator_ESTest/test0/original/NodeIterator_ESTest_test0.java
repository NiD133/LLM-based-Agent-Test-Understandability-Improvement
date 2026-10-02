package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test0 extends NodeIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        Document document0 = Parser.parse("g==i<Tb", "g==i<Tb");
        document0.appendElement("g==i<Tb");
        Class<FormElement> class0 = FormElement.class;
        NodeIterator<FormElement> nodeIterator0 = new NodeIterator<FormElement>(document0, class0);
        boolean boolean0 = nodeIterator0.hasNext();
        assertFalse(boolean0);
    }
}
