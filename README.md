# NostalgiaLib

This plugin contains a few utilities that make plugin development on b1.7.3 a bit easier.

<h3>Current Features</h3>

* PermissionsEx commands framework
* Built-in module system

<h3>Planned Features</h3>

* Automatic plugin updates during runtime

<h2>Setup</h2>

* Gradle
    * Download or build a NostalgiaLib JAR
    * Create a new directory in your project root called <code>libs</code>
    * Put your NostalgiaLib JAR inside the <code>libs</code> directory you just created.
    * In <code>build.gradle</code> add the following to your dependencies: <code>compile files("libs/NostalgiaLib.jar")</code>
* Manual
    * Download or build a NostalgiaLib JAR
    * Create a new directory in your project root called <code>libs</code>
    * Put your NostalgiaLib JAR inside the <code>libs</code> directory you just created.
    * Add the NostalgiaLib JAR to your classpath through your project settings (different for every IDE).

<h2>Build</h2>
Building this project should be a piece of cake!

1) Clone this repository wherever you like.
2) Open the cloned directory in <code>Command Prompt</code> or <code>Terminal</code>
2) Type the following, then press enter: <code>gradlew build</code>

If everything goes well, you should see a big green "BUILD SUCCESSFUL" message. The output JAR should be in <code>build/libs</code>

<b>If you have any issues, questions or concerns, please join <a href="https://beta.oldschoolminecraft.net/goto?k=discord">our Discord server</a>.</b>