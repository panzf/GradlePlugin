package com.zykj.plugin

import org.gradle.api.Project;


public class TransformExtension {
    static HashSet<String> includePackage = []
    static HashSet<String> excludeClass = []
    static String oldDir

    TransformExtension(Project project) {

    }
}