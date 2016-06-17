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

import com.atlassian.clover.CloverDatabase;
import com.atlassian.clover.CoverageDataSpec;
import com.atlassian.clover.api.registry.ClassInfo;
import com.atlassian.clover.api.registry.FileInfo;
import com.atlassian.clover.api.registry.MethodInfo;
import com.atlassian.clover.api.registry.PackageInfo;
import com.atlassian.clover.api.registry.ProjectInfo;

import java.io.PrintStream;

public class SimpleRegistryDumper {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage:");
            System.err.println("java " + SimpleRegistryDumper.class.getName() + " database");
        } else {
            // read clover database together with coverage recording files, use time span=0 (latest build)
            CloverDatabase db = CloverDatabase.loadWithCoverage(args[0], new CoverageDataSpec());
            ProjectInfo projectInfo = db.getRegistry().getProject();
            // print some project details
            printProject(projectInfo, System.out);
        }
    }

    private static void printProject(ProjectInfo db, PrintStream out) {
       for (PackageInfo packageInfo : db.getAllPackages()) {
           out.println("package: " + packageInfo.getName());
           for (FileInfo fileInfo : packageInfo.getFiles()) {
               out.println("\tfile: " + fileInfo.getName());
               for (ClassInfo classInfo : fileInfo.getClasses()) {
                   out.println("\t\tclass: " + classInfo.getName());
                   for (MethodInfo methodInfo : classInfo.getMethods()) {
                       out.println("\t\t\tmethod: " + methodInfo.getName());
                   }
               }
           }
       }
    }
}
