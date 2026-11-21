# HyruleCraft (Fabric, Minecraft 1.20.1)

A fan-made, Zelda-inspired mod: armor, weapons, mobs and iconic gear from OoT, BotW and TotK.
Not affiliated with Nintendo. All art is generated placeholder art - replace it with your own.

## Build / run
Requires JDK 17.
1. Add a Gradle wrapper once:  `gradle wrapper --gradle-version 8.5`   (or copy gradlew/gradle/ from the Fabric example mod)
2. `./gradlew runClient`  - launches Minecraft with the mod
3. `./gradlew build`      - jar ends up in build/libs/
Install the jar in a 1.20.1 Fabric profile together with Fabric API.

## What's inside
**Weapons:** Master Sword (smites undead; right-click at full health = sword beam), Biggoron's Sword (right-click = spin attack),
Boko Club (knockback), Hylian Shield (real blocking shield).
**Gear:** Hookshot, Ocarina of Time (sneak+right-click to choose: Sun's Song, Song of Storms, Epona's Song, Zelda's Lullaby),
Sheikah Slate (Stasis on a mob; sneak+right-click water = Cryonis ice pillar), Korok Leaf (wind gust / glide boost),
Heart Container (+1 permanent heart, keeps through death), Bottled Fairy (full heal), Rupees (5 tiers, exchangeable).
**Armor sets (full-set bonus):** Hero's (Strength), Champion's (Resistance), Zora (water breathing + dolphin's grace), Goron (fire resist + haste).
**Mobs:** Bokoblin, Moblin, Lizalfos, Chuchu (splits like a slime) - spawn naturally and have spawn eggs + loot tables.
Everything is in the "HyruleCraft" creative tab; recipes are in data/hyrulecraft/recipes.

## Tweaking
- Stats: ZeldaToolMaterial / ZeldaArmorMaterial / the *Entity.createAttributes() methods
- Spawn weights: ModEntities.init()
- `python3 tools/generate_assets.py` regenerates placeholder textures, models, lang, recipes, loot tables
