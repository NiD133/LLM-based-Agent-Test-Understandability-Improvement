I’ll use the compile-check skill after editing, and repair-loop only if javac reports errors. First I’m reading the original test and CUT so the refactor keeps the exact behavior.

The original test’s behavior is a single assertion: building with a reader, explicitly setting charset to `null`, then checking the builder resolves a non-null charset. I’m going to reduce the imports and name the intermediate values so that intent is visible without changing the calls.

I’m creating the refactored test file in the working directory now. The edit keeps the original `@Test` method and assertion outcome, but removes unused setup noise and makes the null-charset reset path explicit.

The test file is in place. I’m running the required compile check exactly through `bash compile.sh`; I’ll only edit again if javac reports a concrete error.

COMPILE_OK. The refactored test was created and compiles successfully.