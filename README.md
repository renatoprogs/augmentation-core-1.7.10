# Augmentation Core 1.7.10

Experimental modular augmentation framework for Minecraft 1.7.10 / Forge 10.13.4.1614.

## 0.0.5 — Resource Network

WORLD -> IEnvironment -> resource production -> ResourceNetwork -> storage -> consumers

The resource layer now has an explicit network boundary.

### Current mechanics

- ENERGY and OXYGEN resource types.
- Per-player Body with persistent NBT state.
- Environment abstraction separated from the Body.
- Normal Overworld atmosphere; non-Overworld dimensions currently use vacuum as a conservative placeholder.
- ResourceNetwork with providers, consumers and storage.
- Resource requests pass through the network.
- Organ state tracks integrity, stability and stress.
- Experimental CyberHeart remains a separate augmentation mechanic.

### Architectural rule

The core must not assume that every resource is interchangeable.

An adapter may expose RF, EU, mana, blood, XP, heat, radiation, neural load or another resource later, but conversion must be explicit rather than implicit.

## Next step

0.0.6 should remove the remaining direct environment extraction from Body by introducing an environment resource provider, then make lungs an actual resource processor/provider and introduce explicit physiological failure thresholds.

## Important

A successful repository commit does not imply that the project has been compiled against a local Forge 1.7.10 installation.