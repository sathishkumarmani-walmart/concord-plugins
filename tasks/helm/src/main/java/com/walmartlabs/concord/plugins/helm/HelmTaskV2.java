package com.walmartlabs.concord.plugins.helm;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2021 Walmart Inc., Concord Authors
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

import com.walmartlabs.concord.runtime.v2.sdk.*;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

@Named("helm")
public class HelmTaskV2 implements Task {

    private final DependencyManager dependencyManager;
    private final FileService fileService;

    @Inject
    public HelmTaskV2(DependencyManager dependencyManager, FileService fileService) {
        this.dependencyManager = dependencyManager;
        this.fileService = fileService;
    }

    @Override
    public TaskResult execute(Variables input) throws Exception {
        Path archive = dependencyManager.resolve(URI.create("https://get.helm.sh/helm-v3.5.4-linux-amd64.tar.gz"));

        Path targetDir = fileService.createTempDirectory("helm");

        try (InputStream in = Files.newInputStream(archive);
             InputStream gzin = new GzipCompressorInputStream(in);
             ArchiveInputStream ais = new TarArchiveInputStream(gzin)) {

            ArchiveEntry entry;
            while ((entry = ais.getNextEntry()) != null) {
                Path dst = targetDir.resolve(entry.getName()).normalize();
                if (entry.isDirectory()) {
                    Files.createDirectories(dst);
                } else {
                    Files.copy(ais, dst);
                    System.out.println("!" + dst);
                }
            }
        }

        return TaskResult.success();
    }
}
