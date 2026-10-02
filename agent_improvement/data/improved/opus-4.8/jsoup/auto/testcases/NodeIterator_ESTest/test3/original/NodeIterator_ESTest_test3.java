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
public class NodeIterator_ESTest_test3 extends NodeIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Document document0 = new Document("Gz-OTe\"24", "Wo\u00028Lgsblf?");
        Element element0 = document0.appendElement("org.jsoup.nodes.NodeIterator");
        Class<FormElement> class0 = FormElement.class;
        NodeIterator<FormElement> nodeIterator0 = new NodeIterator<FormElement>(element0, class0);
        nodeIterator0.remove();
        Consumer<Object> consumer0 = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        nodeIterator0.forEachRemaining(consumer0);
    }
}
