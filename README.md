# OW2 stamp/descartes git(lab) issue generator

This project extends the pitmp-maven-plugin, to generate Gitlab issues out of mutation testing:
to provide that, it adds a new output format, GITLAB-ISSUES, to PIT/Descartes.

The generated Gitlab issues will be overwritten (updated) if the plugin is run more than once,
so you can use it multiple times without generating tons of verbose issues...

The content of issues (plain text) is also replicated in text files (in the Descartes output directory).

## Configuration

To use it, just add some configuration to pitmp-maven-plugin, to declare:
- GITLAB-ISSUES as an output format
- Gitlab configuration (optional): if you want Gitlab issues to be injected in your repository, specify the destination
(Gitlab url, project and token).

Results are more complete if fullMutationMatrix=true set, but processing would be slower, and PIT would only allow XML output in addition to GITLAB-ISSUES
(eg. no HTML nor JSON allowed). These limitations are due to PIT/Descartes.
Without fullMutationMatrix set, only critical issues will be detected (mutations that are not detected at all), and processing will be faster.

Example of Descartes plugin configuration to enable GITLAB-ISSUE output:

```
  <plugin>
    <groupId>eu.stamp-project</groupId>
    <artifactId>pitmp-maven-plugin</artifactId>
    <version>1.3.7</version>
    <!-- All PIT's properties can be used. -->
    <dependencies>
      <dependency>
        <groupId>org.ow2.infra</groupId>
        <artifactId>ow2-stamp-descartes-git</artifactId>
        <version>1.0.0</version>
      </dependency>
    </dependencies>
    <!--
      fullMutationMatrix=true optional (required to detect minor issues, but slower processing).
      If set, PIT/Descartes would only allows XML output (no HTML, JSON...)
      Output will be written in reportsDirectory (with same default as for Descartes),
      and optionally to Gitlab if appropriate configuration of destination is provided.
    -->
    <configuration>
      <mutationEngine>descartes</mutationEngine>
      <fullMutationMatrix>true</fullMutationMatrix>
      <reportsDirectory>/tmp/pit-reports</reportsDirectory>
      <outputFormats>
        <value>GITLAB-ISSUES</value>
        <value>XML</value>
      </outputFormats>
      <!-- Sample config of Gitlab destination (if provided, Gitlab issues will be generated) -->
      <pluginConfiguration>
        <gitlabUrl>http://localhost</gitlabUrl>
        <gitlabToken>i_rjH-MM3YuFsvAxCTsH</gitlabToken>
        <gitlabProject>2</gitlabProject>
      </pluginConfiguration>
    </configuration>
  </plugin>
```

## Execution

To run Descartes with the configuration above:

```
mvn eu.stamp-project:pitmp-maven-plugin:descartes
```
(Note: generally requires that your project is built before: run "mvn clean install" prior to running Descartes !)

According to issues discovered by Descartes, 3 files may be generated as output:
- git_critical_issue.txt reports code mutations that are not detected by tests at all (GREEN test suite).
- git_minor_issue.txt reports code mutations that SOME tests do not detect (only if fullMutationMatrix is set to true).
- git_no_coverage.txt reports missing unit tests.

The 2 first ones are replicated as Gitlab issues (if a proper Gitlab configuration is provided).

