# Mammoth
Mammoth is a multi-platform library to manipulate tab lists in Minecraft (Java Edition).

# Notice
This repository contains software that was originally developed for internal use by our organization. We have chosen to open-source this software in the hopes that it may be of use to others.

Please note the following important points:
- While we are making this software available to the public, the only external support we _**might**_ provide is fixing bugs in the existing codebase.
- While we would not accept any external contributions to this project, you might create issues to report bugs.
- We do not guarantee compatibility between releases, please refer to the '[Versioning](#Versioning)' section.

# Supported Software
The required Java version is Java 17.

Currently, the library _**only**_ supports game versions newer than 1.8.

In the future, support for 1.7.x _**might**_ get implemented (legacy versions like 1.7.x are awful and lack a lot of useful features).

# Versioning
We version every release with its shortened revision number.
The reason for this is that we don't guarantee backwards compatibility between releases. 

# Usage
In order to use the API, you have to include the `common` package, and a platform provider of your choice.

*This section is really short and lacks a lot of information about how the library works.*

## Setting Up Mammoth
In the example, the used provider is `mammoth-platform-packetevents`.

Setting up Mammoth is easy. There are multiple ways to create tab list instances for players:

```java
PlatformProvider platform = new MammothPacketEventsProvider(...);
MammothTabListFactory factory = ...;
Mammoth mammoth = new Mammoth(platform, factory); // Providing a factory instance is not a requirement.
// ...
MammothTabList tabList = mammoth.provideTabList(uuid, new OurTemplate());
if (tabList != null) {
    // Store the tab list if it was successfully created.
}
```

```java
PlatformProvider provider = new MammothPacketEventsProvider(...);
MammothTabListFactory factory = ...;
// ...
MammothTabList tabList = factory.provideTabList(uuid, provider, new OurTemplate()); // Using a Mammoth instance is not a requirement.
if (tabList != null) {
    // Store the tab list if it was successfully created.
}
```

## Creating A Tab List Template
In order to create a tab list template, you have to extend `wtf.villain.mammoth.template.MammothTemplate`.
The template class contains several overloaded methods to manipulate the displayed content.

```java
// A basic tab list template.
public class OurTemplate extends MammothTemplate {
    @Override
    public void update() {
        // ...
    }
}
```

For more advanced usage, please refer to the **JavaDoc**.

## Updating / Destroying A Tab List
Mammoth by default doesn't track any tab lists. In order to update / destroy a tab list, you have to grab
a tab list instance, and call the corresponding method.
```java
MammothTabList tabList = ...; // Grabbing a tab list instance.
if (tabList != null) {
    // Updating the tab list.
    tabList.update();
    // ... or destroying the tab list.
    tabList.destroy();
}
```