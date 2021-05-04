package com.walmartlabs.concord.plugins.helm;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2020 Walmart Inc., Concord Authors
 * -----
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =====
 */

import ca.ibodrov.concord.testcontainers.Concord;
import ca.ibodrov.concord.testcontainers.ConcordProcess;
import ca.ibodrov.concord.testcontainers.Payload;
import ca.ibodrov.concord.testcontainers.junit4.ConcordRule;
import com.walmartlabs.concord.client.ProcessEntry;
import com.walmartlabs.concord.common.IOUtils;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * Integration test. Assumes that the current version of the plugin
 * is present in the local Maven repository.
 */
public class HelmTaskIT {

    @ClassRule
    public static final ConcordRule concord = new ConcordRule()
            .mode(Concord.Mode.DOCKER)
            .streamServerLogs(true)
            .streamAgentLogs(true)
            .useLocalMavenRepository(true);

    private static final String CURRENT_VERSION = getCurrentVersion();

    @Test
    public void testWithRuntimeV2() throws Exception {
        test("runtimeV2/concord.yml");
    }

    private void test(String concordYmlSource) throws Exception {
        Payload payload = new Payload()
                .concordYml(new String(readToBytes(concordYmlSource))
                        .replace("%%version%%", CURRENT_VERSION));

        ConcordProcess proc = concord.processes().start(payload);
        proc.waitForStatus(ProcessEntry.StatusEnum.FINISHED);

        proc.assertLog(".*FINISHED.*");
    }

    private static String getCurrentVersion() {
        Properties props = new Properties();
        try (InputStream in = ClassLoader.getSystemResourceAsStream("version.properties")) {
            props.load(in);
            return props.getProperty("version");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readToBytes(String resource) {
        try (InputStream in = HelmTaskIT.class.getResourceAsStream(resource)) {
            return IOUtils.toByteArray(requireNonNull(in, "Can't find the resource: " + resource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
