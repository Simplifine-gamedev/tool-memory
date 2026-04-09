# Tool Memory

A Minecraft Fabric mod for 1.21.1 that makes your tools remember their experience. The more you use a tool for a specific task, the better it gets at that task!

## Features

### Mining Experience
Your pickaxes, axes, shovels, and hoes gain experience for each type of block they mine:
- **Level 1** (100 blocks): +20% mining speed for that block type
- **Level 2** (500 blocks): +40% mining speed for that block type  
- **Level 3** (1000 blocks): +60% mining speed for that block type

### Combat Experience
Your swords track kills for each type of mob:
- **Level 1** (50 kills): +1 damage against that mob type
- **Level 2** (150 kills): +2 damage against that mob type
- **Level 3** (300 kills): +3 damage against that mob type

### Experience Tracking
- XP is stored per individual tool, not globally
- Each tool remembers its own experience separately
- View your tool's XP and bonuses in the item tooltip
- Maximum of 3 levels per material/mob type

## How It Works

1. **Mining**: Every time you break a block with a mining tool (pickaxe, axe, shovel, hoe), the tool gains 1 XP for that specific block type
2. **Combat**: Every time you kill a mob with a sword, the sword gains 1 XP for that specific mob type
3. **Bonuses**: Once you reach the XP thresholds, your tools automatically become more effective
4. **Tooltips**: Hover over your tool to see accumulated XP and current bonuses

## Example

If you mine 100 stone blocks with a diamond pickaxe:
- The pickaxe gains "Stone: 100 XP (Level 1)"
- Mining stone with this pickaxe is now 20% faster
- Mining other blocks is unaffected until you mine 100 of them too

If you kill 50 zombies with an iron sword:
- The sword gains "Zombie: 50 kills (Level 1)"
- This sword now deals +1 extra damage to zombies
- Damage against other mobs is unaffected

## Requirements

- Minecraft 1.21.1
- Fabric Loader 0.16.0+
- Fabric API

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for Minecraft 1.21.1
2. Download [Fabric API](https://modrinth.com/mod/fabric-api)
3. Download the Tool Memory mod JAR from the releases
4. Place both JARs in your `.minecraft/mods` folder
5. Launch the game with the Fabric profile

## Building from Source

```bash
git clone https://github.com/Simplifine-gamedev/tool-memory.git
cd tool-memory
./gradlew build
```

The built JAR will be in `build/libs/`.

## License

MIT License
