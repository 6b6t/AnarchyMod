# AnarchyMod

[![Build](https://github.com/6b6t/AnarchyMod/actions/workflows/build.yml/badge.svg)](https://github.com/6b6t/AnarchyMod/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

🔓 Client-side Fabric mod for anarchy Minecraft servers — unblocks servers and auto-adds 6b6t to the server list.

## Features

- **Server unblocking** — bypasses Mojang's blocked server list for known anarchy servers
- **Auto server list** — automatically adds 6b6t to your server list if not already present
- **Join notification** — sends a lightweight packet to the server on join for analytics

## Supported Minecraft Versions

| Minecraft | Module       |
|-----------|--------------|
| 1.21.8    | `mc-1.21.8`  |
| 1.21.9    | `mc-1.21.9`  |
| 1.21.10   | `mc-1.21.10` |
| 1.21.11   | `mc-1.21.11` |

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) and [Fabric API](https://modrinth.com/mod/fabric-api) for your Minecraft version
2. Download the matching JAR from [Releases](https://github.com/6b6t/AnarchyMod/releases)
3. Place the JAR in your `.minecraft/mods` folder

## Building from Source

Requires Java 21+.

```sh
git clone https://github.com/6b6t/AnarchyMod.git
cd AnarchyMod
./gradlew build
```

Built JARs are located in each module's `build/libs/` directory.

## License

[MIT](LICENSE)
