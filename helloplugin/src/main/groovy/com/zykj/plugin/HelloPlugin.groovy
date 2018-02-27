package com.zykj.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class HelloPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("helloTask") << {
            println "hello gradle plugin"
        }
    }
}