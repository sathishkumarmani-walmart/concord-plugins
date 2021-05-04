package com.walmartlabs.concord.plugins.helm;

import com.walmartlabs.concord.common.IOUtils;
import com.walmartlabs.concord.runtime.v2.sdk.DependencyManager;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A {@link com.walmartlabs.concord.runtime.v2.sdk.DependencyManager} implementation usable in tests.
 */
public class TestDependencyManagerV2 implements DependencyManager {

    private final com.walmartlabs.concord.dependencymanager.DependencyManager delegate;

    public TestDependencyManagerV2() throws IOException {
        Path cacheDir = Paths.get("/tmp/deps");
        Files.createDirectories(cacheDir);
        this.delegate = new com.walmartlabs.concord.dependencymanager.DependencyManager(cacheDir);
    }

    @Override
    public Path resolve(URI uri) throws IOException {
        return delegate.resolveSingle(uri).getPath();
    }
}
