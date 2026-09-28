# Augmentation Core 1.7.10

Experimental modular augmentation framework for Minecraft 1.7.10 / Forge 10.13.4.1614.

## 0.0.9 — Resource demand and physiology profiles

Resource consumption is represented explicitly as demands before organ execution.

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
- ResourceDemandProfile provides a reusable data object for resource type, requested amount and priority.
- Brain and heart consume their demand profile during both allocation and execution.

The current priorities are experimental gameplay values: the default brain profile requests 1 oxygen unit at priority 100, while the default heart profile requests 1 at priority 90. They are configuration examples, not biological claims.

### Physiology profile

PhysiologyProfile now carries both resource-demand parameters and function/stress parameters:

- pressure scale;
- minimum pressure;
- processing range;
- stress threshold;
- stress increase/recovery;
- maximum stress;
- function output at full/failure;
- oxygen demand profile.

Separate default profiles allow different organs to share the same data model without forcing identical priorities or behavior.

### Data-oriented status

- Resource production is separated from the world adapter.
- Extraction efficiency is an explicit data input.
- Lung physiology is calculated by a pure model.
- Resource demand is represented as data before execution.
- Allocation is separated from organ execution.
- Function and stress parameters are represented as profile data rather than repeated literals in organ logic.
- The provider does not depend directly on lungs; it consumes the generic IResourceEfficiencySource contract.
- The body connects compatible organ data to the resource provider.
- Demand profiles can later be supplied by cybernetic organs, filters, suits, enchantments or other systems without changing the resource network.

The project is still partially data-oriented. ResourceNetwork.request() is an execution-phase extraction method and does not itself resolve priority; priority is resolved by allocate(). Organ state such as integrity and stability remains runtime state. Some defaults are still constructed in Java and can later move to external data/configuration.

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


## 0.0.9.x — physiological collapse

The physiology layer now distinguishes four experimental systemic states:

- NORMAL
- STRAIN
- FAILURE
- COLLAPSE

CollapseModel is a pure calculation layer. It combines stress, integrity and stability instead of treating collapse as a single counter. Each organ exposes its configured maximum stress, so the normalization remains data-driven.

The current thresholds are gameplay placeholders for testing the state-space transition and are not intended as biological claims.

Systemic body state is derived from the most severe current organ state and persisted in NBT. This is intentionally a small first step; blood/internal transport will be introduced separately rather than making blood equivalent to external resources.


## Internal blood medium

A minimal `IBloodSystem`/`BloodSystem` layer now separates internal transport from external resources. Lungs request environmental oxygen through the ResourceNetwork and convert the supplied amount into blood oxygen. Brain and heart consume oxygen from blood instead of directly consuming the external OXYGEN storage.

This establishes the intended boundary:

`environmental resource -> resource network -> organ conversion -> blood -> organ consumption`

The current blood model only carries oxygen. Nutrients, waste, temperature, pressure and volume remain future state dimensions and are not yet simulated.


## Internal blood allocation

The internal blood medium now has its own demand/allocation phase, separate from the external resource network.

The current cycle is:

```text
External environment
      ↓
ResourceNetwork
      ↓
Lungs
      ↓
BloodSystem
      ↓
BloodDemand[] + priority allocation
      ↓
Brain / Heart
      ↓
Stress / Failure / Collapse
```

Blood oxygen is finite. Each organ declares an internal oxygen demand with a priority, and the blood system allocates the available oxygen deterministically: higher priority first, then consumer id as a stable tie-breaker. The allocation is consumed from the blood pool immediately and the provided amount is exposed to the organ through the body context.

This is intentionally limited to oxygen. Nutrients, waste, temperature, pressure and volume remain future extensions until the oxygen conservation loop is validated.

