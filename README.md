# Augmentation Core 1.7.10

Experimental modular augmentation framework for Minecraft 1.7.10 / Forge 10.13.4.1614.

## 0.0.7 — Data-oriented physiology

The physiology layer now separates data from calculation.

WORLD -> IEnvironment -> EnvironmentResourceProvider -> ResourceNetwork -> Storage -> Consumers

ORGAN STATE + ENVIRONMENT DATA -> PhysiologyModel -> FUNCTION/STRESS

### Data-oriented boundary

PhysiologyProfile contains experimental thresholds and coefficients. PhysiologyModel contains pure calculations over those inputs. BiologicalLungs owns organ state and delegates calculations to the model.

This removes the main physiological threshold literals from the organ tick. The current defaults remain experimental gameplay parameters, not biological constants.

### Architecture status

The core is partially data-oriented, not fully data-oriented yet. The resource network separates providers, consumers and storage, but ResourceNetwork.request() still ignores consumer priority during allocation. Several organ behaviors also still contain fixed values.

The environment adapter remains responsible for translating world conditions into resources; Body no longer calls environment extraction directly.

### Important limitation

The lungs are still a physiological function processor, not a true oxygen extractor. The environment provider currently supplies a bounded OXYGEN amount per tick before physiological processing. The next resource step should make extraction efficiency affect how much environmental resource becomes available to the network.

A successful repository commit does not imply that the project has been compiled against a local Forge 1.7.10 installation.
