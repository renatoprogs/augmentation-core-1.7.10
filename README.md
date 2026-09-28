# Augmentation Core 1.7.10

Experimental modular augmentation framework for Minecraft 1.7.10 / Forge 10.13.4.1614.

## 0.0.9 — Resource demand and priority allocation

Resource consumption is now represented explicitly as demands before organ execution.

WORLD -> IEnvironment -> EnvironmentResourceProvider -> ResourceNetwork -> Storage
                                      |
                                      v
                              ResourceDemand[]

ORGAN -> collectResourceDemands()
             |
             v
      requested amount + priority
             |
             v
      ResourceNetwork.allocate()
             |
             v
      provided amount / deficit
             |
             v
         BodyContext
             |
             v
         organ.tick()

### Demand model

- IResourceDemand separates requested, provided and deficit amounts.
- ResourceDemand is the default implementation.
- ResourceNetwork.allocate() orders demands by priority before consuming shared storage.
- BodyContext applies the allocated amount to the execution phase.
- Brain and heart no longer hardcode their oxygen demand amount/priority directly inside collectResourceDemands().
- ResourceDemandProfile provides a small reusable data object for resource type, requested amount and priority.

The current priorities are experimental gameplay values: the default brain profile requests 1 oxygen unit at priority 100, while the heart requests 1 at priority 90. They are configuration examples, not biological claims.

### Data-oriented status

- Resource production is separated from the world adapter.
- Extraction efficiency is an explicit data input.
- Lung physiology is calculated by a pure model.
- Resource demand is represented as data before execution.
- Allocation is separated from organ execution.
- The provider does not depend directly on lungs; it consumes the generic IResourceEfficiencySource contract.
- The body connects compatible organ data to the resource provider.
- Physiological thresholds and coefficients remain in PhysiologyProfile.
- Demand profiles can later be supplied by cybernetic organs, filters, suits, enchantments or other systems without changing the resource network.

The project is still partially data-oriented. ResourceNetwork.request() is an execution-phase extraction method and does not itself resolve priority; priority is resolved by allocate(). Some physiological behavior and default values remain hardcoded and are candidates for further extraction into data profiles.

### Current extraction model

For the current experimental profile:

extraction_efficiency = f(pressure, mean(integrity, stability))

The result is expressed as a percentage and applied to the environmental resource before it enters the network.

This is an experimental gameplay model, not a biological claim.

A successful repository commit does not imply that the project has been compiled against a local Forge 1.7.10 installation.

## Licensing and attribution

This project is released under the MIT License.

The project is an independent Minecraft 1.7.10 reimplementation/adaptation. Its
architecture and code are being rewritten for Forge 1.7.10 rather than copied as
a version-for-version port of Cyberware.

The design research uses Flaxbeard's **Cyberware** project as an upstream reference
for documented augmentation mechanics and concepts. Cyberware was released under
the MIT License and is Copyright (c) 2016 Flaxbeard.

Original project:
- https://github.com/Flaxbeard/Cyberware

The MIT attribution is retained in this repository's LICENSE file. This project
does not imply endorsement by Flaxbeard or the original Cyberware team.
