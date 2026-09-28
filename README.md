# Augmentation Core 1.7.10

Experimental framework for rebuilding the Cyberware mechanics as a modular augmentation core on Minecraft 1.7.10 / Forge 10.13.4.1614.

## Current state — 0.0.4

- Resource API: ENERGY and OXYGEN.
- Per-player Body and persistent resource state.
- Environment abstraction separated from the Body.
- Deterministic atmosphere model: normal, thin, vacuum.
- Forge 1.7.10 environment adapter.
- First experimental CyberHeart augmentation.
- Java source level 1.7.

This is **not yet a blind port of Cyberware**. The original Cyberware mechanics are being reconstructed as testable modules.

## Architecture

WORLD → IEnvironment → Atmosphere → Resource layer → Body → organs/augmentations → functions/stress

The next target is the resource network and gradual physiological failure model.

## Important

The project is experimental. A successful repository commit does not imply that the project has been compiled against a local Forge 1.7.10 installation.