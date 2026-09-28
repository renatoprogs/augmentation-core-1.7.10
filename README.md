# Augmentation Core 1.7.10

Experimental modular augmentation framework for Minecraft 1.7.10 / Forge 10.13.4.1614.

## 0.0.8 — Data-driven resource extraction

Environmental resource production is now affected by an explicit efficiency supplied by a generic data source.

WORLD -> IEnvironment -> EnvironmentResourceProvider -> ResourceNetwork -> Storage -> Consumers
                                      ^
                                      |
                         IResourceEfficiencySource

ORGAN STATE + ENVIRONMENT DATA -> PhysiologyModel -> EXTRACTION EFFICIENCY

### Data-oriented status

- Resource production is separated from the world adapter.
- Extraction efficiency is an explicit data input.
- Lung physiology is calculated by a pure model.
- The provider does not depend directly on lungs; it consumes the generic IResourceEfficiencySource contract.
- The body connects compatible organ data to the resource provider.
- Physiological thresholds and coefficients remain in PhysiologyProfile.
- The same contract can later be supplied by cybernetic lungs, filters, suits, enchantments or other systems without changing the environment provider.

The project is still partially data-oriented. ResourceNetwork.request() does not yet resolve consumer priority, and some organ behavior still contains fixed gameplay values.

### Current extraction model

For the current experimental profile:

extraction_efficiency = f(pressure, mean(integrity, stability))

The result is expressed as a percentage and applied to the environmental resource before it enters the network.

This is an experimental gameplay model, not a biological claim.

A successful repository commit does not imply that the project has been compiled against a local Forge 1.7.10 installation.
