# CADisplay

CloudNet tablist and chat handler with api to manage

## Setup

Maven:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>`
    <dependency>
        <groupId>com.github.coding-area-net</groupId>
        <artifactId>CADisplay</artifactId>
        <version>${VERSION}</version>
    </dependency>
</dependencies>
```

Gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.coding-area-net:CADisplay:${VERSION}'
}
```

## API Examples

### Modify Tablist:
```java
DisplayAPI.getDisplayAPI().setCurrentTabDisplay(new CustomTeamsTabDisplay(player -> {
    Team team = teams.get(player);
    if (team == null) {
    	return TabEntry.builder()
            .name("Spec")
            .prefix("§r§8┃ §7Spec §8» §7")
            .build();
    }
    
    return TabEntry.builder() // Builder for scoreboard teams
        .name(team.getName())
        .prefix("§8┃ " + team.getColor() + team.getName() + " §8» ")
        .display(team.getName())
        .color(team.getColor())
        .build();
}));
```

### Modify Chat Format:

#### Dynamic (Different for every player)
```java
DisplayAPI.getDisplayAPI().setCurrentChatDisplay(new DynamicChatDisplay(player -> {
	Team team = teams.get(player);
	if (team == null) return DisplayAPI.getDisplayAPI().getDefaultChatFormat();
	return DisplayAPI.getDisplayAPI().getDefaultChatFormat().replace("%display%", team.getDisplayName()).replace("%color%", team.getColor());
}));
```

#### Static (Same for every player)
```java
DisplayAPI.getDisplayAPI().setCurrentChatDisplay(new StaticChatDisplay("§8§l┃ %display% §8┃ %color%%name% &8» §7%message%"));
```

## Reset back to defaults

To reset the tablist or chat back to default use following methods:

```java
DisplayAPI.getDisplayAPI().resetTabDisplay();
DisplayAPI.getDisplayAPI().resetChatDisplay();
```

## Deactivate custom chat or tab

Setting the displays to null will deactivate it

```java
DisplayAPI.getDisplayAPI().setCurrentTabDisplay(null);
DisplayAPI.getDisplayAPI().setCurrentChatDisplay(null);
```

## GitFlows

Uses: [gitflow-maven-plugin](https://github.com/aleksandr-m/gitflow-maven-plugin)

- `gitflow:release-start` - Starts a release branch and updates version(s) to release version.
- `gitflow:release-finish` - Merges a release branch and updates version(s) to next development version.
- `gitflow:release` - Releases project w/o creating a release branch.
- `gitflow:feature-start` - Starts a feature branch and optionally updates version(s).
- `gitflow:feature-finish` - Merges a feature branch.
- `gitflow:hotfix-start` - Starts a hotfix branch and updates version(s) to hotfix version.
- `gitflow:hotfix-finish` - Merges a hotfix branch.
- `gitflow:support-start` - Starts a support branch from the production tag.
- `gitflow:help` - Displays help information.
