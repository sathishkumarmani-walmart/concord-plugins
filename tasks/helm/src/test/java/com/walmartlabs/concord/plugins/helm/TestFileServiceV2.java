package com.walmartlabs.concord.plugins.helm;

import com.walmartlabs.concord.common.IOUtils;
import com.walmartlabs.concord.runtime.v2.sdk.FileService;

import java.io.IOException;
import java.nio.file.Path;

public class TestFileServiceV2 implements FileService {

    @Override
    public Path createTempFile(String prefix, String suffix) throws IOException {
        return IOUtils.createTempFile(prefix, suffix);
    }

    @Override
    public Path createTempDirectory(String prefix) throws IOException {
        return IOUtils.createTempDir(prefix);
    }
}
