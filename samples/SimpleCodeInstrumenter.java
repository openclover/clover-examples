/*
 * Copyright 2009 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.atlassian.clover.api.CloverException;
import com.atlassian.clover.api.instrumentation.InstrumentationSession;
import com.atlassian.clover.api.registry.FileInfo;
import com.atlassian.clover.context.ContextSet;
import com.atlassian.clover.registry.Clover2Registry;
import com.atlassian.clover.registry.FixedSourceRegion;
import com.atlassian.clover.registry.entities.MethodSignature;
import com.atlassian.clover.registry.entities.Modifier;
import com.atlassian.clover.registry.entities.Modifiers;
import com.atlassian.clover.registry.entities.Parameter;
import com.atlassian.clover.spi.lang.LanguageConstruct;

import java.io.File;
import java.io.IOException;

public class SimpleCodeInstrumenter {
    private Clover2Registry registry;
    private InstrumentationSession session;

    public SimpleCodeInstrumenter(String initString, String projectName) throws CloverException {
        try {
            final File dbFile = new File(initString);
            registry = Clover2Registry.createOrLoad(dbFile, projectName);
            if (registry == null) {
                throw new CloverException("Unable to create or load clover registry located at: " + dbFile);
            }
        } catch (IOException e) {
            throw new CloverException(e);
        }
    }

    public void startInstrumentation(String encoding) throws CloverException {
        session = registry.startInstr(encoding);
    }

    public Clover2Registry endInstrumentation(boolean append) throws CloverException {
        try {
            session.close();
            if (append) {
                registry.saveAndAppendToFile();
            } else {
                registry.saveAndOverwriteFile();
            }
            return registry;
        } catch (IOException e) {
            throw new CloverException(e);
        }
    }

    /**
     * This method should perform the actual instrumentation. On every code construct you find in your
     * source file(s) being instrumented (such as file, class, method, statement, branch) you shall call
     * proper handler from InstrumentationSession class in order to record data for a given code entity
     * in the Clover database.
     */
    public void instrument() {
        // note: there is no need to call session.enterPackage(packageName), it will be called from
        // session.enterFile(); the same applies to session.exitPackage()

        // example: register a file with attributes such as enclosing package, number of lines, time stamp, checksum
        String packageName = "com.acme.my.package";
        File sourceFile =  new File("com/acme/my/package/Foo.java");
        FileInfo fileInfo = session.enterFile(packageName, sourceFile,
                200, 100, sourceFile.lastModified(), sourceFile.length(), 3423452);

        // example: register a class (in current file)
        session.enterClass("Foo",                            // class name
                new FixedSourceRegion(10, 1),                // start row:column
                Modifiers.createFrom(Modifier.PUBLIC, null), // modifiers and annotations
                false, false, false);                        // is interface, is enum, is annotation

        // example: add a method to the Foo class
        MethodSignature methodSignature = new MethodSignature("helloWorld", null,  // method name and generic type
                "void",                                                            // return type
                new Parameter[] { new Parameter("String", "name") },               // formal parameters
                null,                                                              // throws
                Modifiers.createFrom(Modifier.PROTECTED | Modifier.STATIC, null)); // modifiers
        session.enterMethod(new ContextSet(),
                new FixedSourceRegion(12, 1),  // start row:column
                methodSignature,  // signature
                false,            // is test method
                null,             // test name (if different from the method name)
                false,            // is lambda function
                5,                // cyclomatic complexity
                LanguageConstruct.Builtin.METHOD);

        // example: add a statement in the helloWorld method
        session.addStatement(new ContextSet(),
                new FixedSourceRegion(13, 1, 13, 44),  // start row:column, end row:column
                3,                                     // cyclomatic complexity
                LanguageConstruct.Builtin.STATEMENT);

        // end method, class and a file
        session.exitMethod(14, 1);  // end row:column
        session.exitClass(30, 2);   // end row:column
        session.exitFile();
    }

    public static void main(String[] args) throws CloverException {
        if (args.length != 1) {
            System.err.println("Usage:");
            System.err.println("java " + SimpleCodeInstrumenter.class.getName() + " database");
        } else {
            SimpleCodeInstrumenter instrumenter = new SimpleCodeInstrumenter(args[0], "MyProject");
            instrumenter.startInstrumentation("UTF-8");
            instrumenter.instrument();
            instrumenter.endInstrumentation(true);
        }
    }
}
