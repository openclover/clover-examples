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
import com.atlassian.clover.recorder.FileBasedGlobalCoverageRecording;
import com.atlassian.clover.registry.Clover2Registry;
import com_atlassian_clover.Clover;

import java.io.File;
import java.io.IOException;

public class GlobalCoverageFileGenerator {

    private static void convertCoverage(final Clover2Registry registry) throws IOException {
        // TODO fill this array with hit counts, e.g. from another coverage tool
        int[] elements = { 1, 2, 3, 4, 5 };
        // note: should be >= from registry.getVersion() otherwise will be discarded as outdated
        long coverageTS = registry.getVersion();
        // generate global recording file name (<clover.db><randomhash>_<timestamp>)
        String recname = Clover.getRecordingName(12345, registry.getRegistryFile().getAbsolutePath(), coverageTS);

        // create global coverage file
        flushToDisk(recname, registry.getVersion(), coverageTS, elements);
    }

    /**
     * @param dbname name of the coverage recording file
     * @param dbversion database timestamp
     * @param writeTS coverage file timestamp
     * @param elements an array with hit counts
     */
    private static void flushToDisk(final String dbname, long dbversion, long writeTS, int[] elements) throws IOException {
        new FileBasedGlobalCoverageRecording(dbname, dbversion, writeTS, new int[][] {elements}, elements.length).write();
    }

    public static void main(String[] args) throws IOException, CloverException {
        final Clover2Registry registry = Clover2Registry.fromFile(new File("clover.db"));
        convertCoverage(registry);
    }
}
