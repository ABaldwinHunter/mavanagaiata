/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2011, Sebastian Staudt
 */

package com.github.koraktor.mavanagaiata;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;

import org.junit.Test;

public class GitTagMojoTest extends AbstractMojoTest<GitTagMojo> {

    @Test
    public void testError() {
        super.testError("Unable to read Git tag");
    }

    @Test
    public void testResult() throws IOException, MojoExecutionException {
        this.mojo.execute();

        String abbrev = this.headId.substring(0, 7);

        this.assertProperty("2.0.0-2-g" + abbrev, "tag.describe");
        this.assertProperty("2.0.0", "tag.name");
    }

    @Test
    public void testDifferentHead() throws IOException, MojoExecutionException {
        this.mojo.head = "HEAD^^";
        this.mojo.execute();

        this.assertProperty("2.0.0", "tag.describe");
        this.assertProperty("2.0.0", "tag.name");
    }

    @Test
    public void testUntaggedBranch() throws IOException, MojoExecutionException {
        this.mojo.head = "gh-pages";
        this.mojo.execute();

        this.assertProperty("a0e1305", "tag.describe");
        this.assertProperty("", "tag.name");
    }

    @Test
    public void testUntaggedProject() throws IOException, MojoExecutionException {
        this.mojo.gitDir = new File("src/test/resources/untagged-project/_git");
        this.mojo.initRepository();
        this.mojo.execute();

        this.assertProperty("2be4488", "tag.describe");
        this.assertProperty("", "tag.name");
    }

}
