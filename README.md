# Augmentation Core 1.7.10

Experimental modular augmentation framework for Minecraft 1.7.10 / Forge 10.13.4.1614.

## 0.0.6 — Environment provider boundary

The environment no longer injects oxygen directly into Body storage.

WORLD -> IEnvironment -> EnvironmentResourceProvider -> ResourceNetwork -> Storage -> Consumers

This is an important architectural boundary: the Body sees a resource network, not the world as a resource source.

### Current mechanics

- ENERGY and OXYGEN resource types.
- Per-player Body with persistent NBT state.
- Environment abstraction separated from Body.
- Normal Overworld atmosphere; non-Overworld dimensions currently use vacuum as a conservative placeholder.
- Explicit resource providers/consumers and storage.
- EnvironmentResourceProvider translates environmental availability into a network provider.
- Organ state tracks integrity, stability and stress.
- Experimental CyberHeart remains a separate augmentation mechanic.

### Important limitation

The lungs are currently a physiological function processor, not yet a true oxygen extractor. That is deliberate: the next step is to model extraction efficiency from pressure and lung condition instead of hiding it in the world adapter.

### Next step

0.0.7 should introduce explicit physiological failure thresholds and make lung extraction efficiency a function of pressure, integrity and stability.

A successful repository commit does not imply that the project has been compiled against a local Forge 1.7.10 installation.