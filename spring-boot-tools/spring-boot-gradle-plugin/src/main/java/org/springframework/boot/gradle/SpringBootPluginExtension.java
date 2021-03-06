/*
 * Copyright 2012-2018 the original author or authors.
 *
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
 */

package org.springframework.boot.gradle;

import java.io.File;
import java.util.Map;
import java.util.Set;

import groovy.lang.Closure;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;

import org.springframework.boot.gradle.buildinfo.BuildInfo;
import org.springframework.boot.loader.tools.Layout;
import org.springframework.boot.loader.tools.LayoutFactory;
import org.springframework.boot.loader.tools.Layouts;

/**
 * Gradle DSL Extension for 'Spring Boot'. Most of the time Spring Boot can guess the
 * settings in this extension, but occasionally you might need to explicitly set one or
 * two of them. E.g.
 *
 * <pre>
 *     apply plugin: 'org.springframework.boot'
 *     springBoot {
 *         mainClass = 'org.demo.Application'
 *         layout = 'ZIP'
 *     }
 * </pre>
 *
 * @author Phillip Webb
 * @author Dave Syer
 * @author Stephane Nicoll
 * @author Andy Wilkinson
 */
public class SpringBootPluginExtension {

	private final Project project;

	/**
	 * The main class that should be run. Instead of setting this explicitly you can use
	 * the 'mainClassName' of the project or the 'main' of the 'run' task. If not
	 * specified the value from the MANIFEST will be used, or if no manifest entry is the
	 * archive will be searched for a suitable class.
	 */
	String mainClass;

	/**
	 * The classifier (file name part before the extension). Instead of setting this
	 * explicitly you can use the 'classifier' property of the 'bootRepackage' task. If
	 * not specified the archive will be replaced instead of renamed.
	 */
	String classifier;

	/**
	 * The name of the ivy configuration name to treat as 'provided' (when packaging those
	 * dependencies in a separate path). If not specified 'providedRuntime' will be used.
	 */
	String providedConfiguration;

	/**
	 * The name of the custom configuration to use.
	 */
	String customConfiguration;

	/**
	 * If the original source archive should be backed-up before being repackaged.
	 */
	boolean backupSource = true;

	/**
	 * The layout of the archive if it can't be derived from the file extension. Valid
	 * values are JAR, WAR, ZIP, DIR (for exploded zip file). ZIP and DIR are actually
	 * synonymous, and should be used if there is no MANIFEST.MF available, or if you want
	 * the MANIFEST.MF 'Main-Class' to be PropertiesLauncher. Gradle will coerce literal
	 * String values to the correct type.
	 */
	LayoutType layout;

	/**
	 * The layout factory that will be used when no explicit layout is specified.
	 * Alternative layouts can be provided by 3rd parties.
	 */
	LayoutFactory layoutFactory;

	/**
	 * Libraries that must be unpacked from fat jars in order to run. Use Strings in the
	 * form {@literal groupId:artifactId}.
	 */
	Set<String> requiresUnpack;

	/**
	 * Whether Spring Boot Devtools should be excluded from the fat jar.
	 */
	boolean excludeDevtools = true;

	/**
	 * Location of an agent jar to attach to the VM when running the application with
	 * runJar task.
	 */
	File agent;

	/**
	 * Flag to indicate that the agent requires -noverify (and the plugin will refuse to
	 * start if it is not set).
	 */
	Boolean noverify;

	/**
	 * If exclude rules should be applied to dependencies based on the
	 * spring-dependencies-bom.
	 */
	boolean applyExcludeRules = true;

	/**
	 * If a fully executable jar (for *nix machines) should be generated by prepending a
	 * launch script to the jar.
	 */
	boolean executable = false;

	/**
	 * The embedded launch script to prepend to the front of the jar if it is fully
	 * executable. If not specified the 'Spring Boot' default script will be used.
	 */
	File embeddedLaunchScript;

	/**
	 * Properties that should be expanded in the embedded launch script.
	 */
	Map<String, String> embeddedLaunchScriptProperties;

	public SpringBootPluginExtension(Project project) {
		this.project = project;
	}

	/**
	 * Convenience method for use in a custom task.
	 * @return the Layout to use or null if not explicitly set
	 */
	public Layout convertLayout() {
		return (this.layout == null ? null : this.layout.layout);
	}

	public String getMainClass() {
		return this.mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public String getClassifier() {
		return this.classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

	public String getProvidedConfiguration() {
		return this.providedConfiguration;
	}

	public void setProvidedConfiguration(String providedConfiguration) {
		this.providedConfiguration = providedConfiguration;
	}

	public String getCustomConfiguration() {
		return this.customConfiguration;
	}

	public void setCustomConfiguration(String customConfiguration) {
		this.customConfiguration = customConfiguration;
	}

	public boolean isBackupSource() {
		return this.backupSource;
	}

	public void setBackupSource(boolean backupSource) {
		this.backupSource = backupSource;
	}

	public LayoutType getLayout() {
		return this.layout;
	}

	public void setLayout(LayoutType layout) {
		this.layout = layout;
	}

	public LayoutFactory getLayoutFactory() {
		return this.layoutFactory;
	}

	public void setLayoutFactory(LayoutFactory layoutFactory) {
		this.layoutFactory = layoutFactory;
	}

	public Set<String> getRequiresUnpack() {
		return this.requiresUnpack;
	}

	public void setRequiresUnpack(Set<String> requiresUnpack) {
		this.requiresUnpack = requiresUnpack;
	}

	public boolean isExcludeDevtools() {
		return this.excludeDevtools;
	}

	public void setExcludeDevtools(boolean excludeDevtools) {
		this.excludeDevtools = excludeDevtools;
	}

	public File getAgent() {
		return this.agent;
	}

	public void setAgent(File agent) {
		this.agent = agent;
	}

	public Boolean getNoverify() {
		return this.noverify;
	}

	public void setNoverify(Boolean noverify) {
		this.noverify = noverify;
	}

	public boolean isApplyExcludeRules() {
		return this.applyExcludeRules;
	}

	public void setApplyExcludeRules(boolean applyExcludeRules) {
		this.applyExcludeRules = applyExcludeRules;
	}

	public boolean isExecutable() {
		return this.executable;
	}

	public void setExecutable(boolean executable) {
		this.executable = executable;
	}

	public File getEmbeddedLaunchScript() {
		return this.embeddedLaunchScript;
	}

	public void setEmbeddedLaunchScript(File embeddedLaunchScript) {
		this.embeddedLaunchScript = embeddedLaunchScript;
	}

	public Map<String, String> getEmbeddedLaunchScriptProperties() {
		return this.embeddedLaunchScriptProperties;
	}

	public void setEmbeddedLaunchScriptProperties(
			Map<String, String> embeddedLaunchScriptProperties) {
		this.embeddedLaunchScriptProperties = embeddedLaunchScriptProperties;
	}

	public void buildInfo() {
		this.buildInfo(null);
	}

	public void buildInfo(Closure<?> taskConfigurer) {
		BuildInfo bootBuildInfo = this.project.getTasks().create("bootBuildInfo",
				BuildInfo.class);
		this.project.getTasks().getByName(JavaPlugin.CLASSES_TASK_NAME)
				.dependsOn(bootBuildInfo);
		if (taskConfigurer != null) {
			taskConfigurer.setDelegate(bootBuildInfo);
			taskConfigurer.call();
		}
	}

	/**
	 * Layout Types.
	 */
	public enum LayoutType {

		/**
		 * Executable JAR layout.
		 */
		JAR(new Layouts.Jar()),

		/**
		 * Executable WAR layout.
		 */
		WAR(new Layouts.War()),

		/**
		 * Executable expanded archive layout.
		 */
		ZIP(new Layouts.Expanded()),

		/**
		 * Executable expanded archive layout.
		 */
		DIR(new Layouts.Expanded()),

		/**
		 * Module Layout.
		 * @deprecated as of 1.5 in favor of a custom {@link LayoutFactory}
		 */
		@Deprecated
		MODULE(new Layouts.Module()),

		/**
		 * No layout.
		 */
		NONE(new Layouts.None());

		Layout layout;

		LayoutType(Layout layout) {
			this.layout = layout;
		}

	}

}
