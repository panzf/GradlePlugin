package com.zykj.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.zykj.plugin.helper.Utils
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils

public class TransformImpl extends Transform {
    public Project project

    public TransformImpl(Project project) {
        this.project = project
        project.extensions.create('hotpatch', TransformExtension, project)
    }

    @Override
    public String getName() {//设置我们自定义的 transform 对应的task名称
        return "TransformImpl"
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {//指定输入类型，确保其他文件类型不被传入
        return TransformManager.CONTENT_CLASS
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {//指定 transform 的作用范围
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    public boolean isIncremental() {
        return false
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        def extension = project.extensions.findByName('hotpatch') as TransformExtension
        project.logger.error extension.includePackage
        project.logger.error extension.excludeClass
        project.logger.error extension.oldDir
        /**
         * 遍历输入文件
         */
        inputs.each { TransformInput input ->
            /**
             * 遍历目录
             */
            input.directoryInputs.each { DirectoryInput directoryInput ->
                /**
                 * 获得产物的目录
                 */
                File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY);
                //这里进行我们的处理 TODO
                project.logger.error "Copying ${directoryInput.name} to ${dest.absolutePath}"
                /**
                 * 处理完后拷到目标文件
                 */
                FileUtils.copyDirectory(directoryInput.file, dest);

            }

            /**
             * 遍历jar
             */
            input.jarInputs.each { JarInput jarInput ->
                String destName = jarInput.name;
                /**
                 * 重名名输出文件,因为可能同名,会覆盖
                 */
                def hexName = Utils.md5Hex(jarInput.file.absolutePath);
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4);
                }
                /**
                 * 获得输出文件
                 */
                File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR);

                //处理jar进行字节码注入处理 TODO

                project.logger.error "Copying ${jarInput.file.absolutePath} to ${dest.absolutePath}"
                /**
                 * 处理完后拷到目标文件
                 */
                FileUtils.copyFile(jarInput.file, dest);
            }
        }
    }


}