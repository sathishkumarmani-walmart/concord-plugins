package com.walmartlabs.concord.plugins.helm;

import com.walmartlabs.concord.runtime.v2.sdk.DependencyManager;
import com.walmartlabs.concord.runtime.v2.sdk.FileService;
import com.walmartlabs.concord.runtime.v2.sdk.MapBackedVariables;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HelmTaskTest {

    @Test
    public void test() throws Exception {
        DependencyManager dependencyManager = new TestDependencyManagerV2();
        FileService fileService = new TestFileServiceV2();

        Map<String, Object> input = new HashMap<>();

        HelmTaskV2 task = new HelmTaskV2(dependencyManager, fileService);
        task.execute(new MapBackedVariables(input));
    }
}
