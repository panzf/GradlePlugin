package com.zykj.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class TransformPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        def log = project.logger
        log.info "======================="
        log.info "===   开始修改class  ==="
        log.info "======================="
        project.android.registerTransform(new TransformImpl(project))
    }
}