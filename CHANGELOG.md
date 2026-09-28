# Changelog

All notable project changes are recorded here by mod version. Persistence schema changes are tracked independently.

## 0.0.10 — current

### Physiological state progression
- Added accumulated wear as a separate state from acute stress.
- Added stability degradation/recovery driven by physiological load.
- Added integrity degradation driven by accumulated wear.
- Persisted organ physiological state in NBT.
- NBT schema advanced from 9 to 10.

## 0.0.9

### Resource demand and physiology
- Introduced explicit `IResourceDemand` / `ResourceDemand` allocation data.
- Added priority-based external resource allocation.
- Added `ResourceDemandProfile` to keep demand parameters out of organ execution logic.
- Added `PhysiologyProfile` for pressure, processing, stress and function parameters.
- Propagated resource deficits into physiological stress.

### Internal blood medium
- Added `IBloodSystem`, `BloodSystem`, `IBloodDemand` and `BloodDemand`.
- Separated external resources from the internal oxygen transport medium.
- Added deterministic internal oxygen allocation by priority and consumer id.
- Brain and heart now consume allocated blood oxygen instead of directly consuming external oxygen.

### Physiological collapse
- Added `CollapseState`: NORMAL, STRAIN, FAILURE and COLLAPSE.
- Added pure `CollapseModel` evaluation.
- Systemic state is derived from the most severe current organ state.
- Collapse thresholds are explicitly experimental gameplay parameters.

### Persistence
- NBT schema version is **9**.
- The NBT schema number is independent from the mod version.

### Verification status
- Repository changes are committed to GitHub.
- Local Forge 1.7.10 compilation has not been verified in the current environment.

## 0.0.8

- Introduced data-driven resource extraction efficiency.
- Added `IResourceEfficiencySource`.
- Connected lung physiology to the generic environment resource provider.
- NBT schema version: 5.

## 0.0.7

- Introduced physiology profiles and a pure physiology calculation layer.
- Separated pressure, condition, processing and stress parameters from organ logic.

## 0.0.6

- Removed the direct Body-to-environment resource extraction path.
- Added `IEnvironmentResourceProvider` and `EnvironmentResourceProvider`.
- NBT schema version: 4.

## 0.0.5

- Added the resource network layer.
- Introduced providers, consumers, storage and priority-aware resource flow.

## 0.0.4

- Initial Forge 1.7.10 modular body/resource foundation.
- Added biological brain, heart and lungs.
- Added player data persistence through the Forge 1.7.10 extended entity properties mechanism.
