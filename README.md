# Carpet PVP — Fabric 1.20.1 backport

An unofficial Fabric 1.20.1 backport of the fake-player PvP fixes in Carpet PVP 1.2.0, the release for Minecraft 1.21 and 1.21.1. It is based on the MIT-licensed [Fabric Carpet 1.20 source](https://github.com/gnembon/fabric-carpet/tree/1.4.112) and keeps Carpet's existing commands, rules, and Scarpet features.

This targets the upstream 1.2.0 release for 1.21/1.21.1. It does not include the later 1.4.0 changes published for 1.21.5.

## Backported behavior

- Fake players use separate reach limits for blocks and entities.
- Attack actions swing and reset the attack cooldown even when they miss.
- Knockback-enchanted attacks do not add knockback when a hit is accepted during the target's hurt-resistance window.
- Shield blocks by fake players use the expected shield sounds, stats, and damage trigger without the unwanted knockback behavior.
- Fake players slow down while using items, including bows, shields, and consumables.
- Fake players can sneak and sprint at the same time.
- Starting another continuous action of the same type does not stop the action already running.
- Block use swings on each interaction attempt, including attempts that do not consume the action.
- Fake players return to their original spawn position after death.
- `/player disconnect` disconnects a fake player, and `/player s` targets the command executor for use with `/execute as`.
- The `shieldStunning` Carpet rule lets a follow-up hit land immediately after an attacker disables a shield.

## Compatibility

- Minecraft 1.20.1
- Fabric Loader 0.14.19 or newer
- Java 17 or newer
- Fabric Carpet is included in this jar. Do not install a second Carpet jar alongside it.

## Source and license

This is an unofficial community backport. It is not affiliated with or endorsed by gnembon, TheobaldTheBird, or the Fabric Carpet maintainers. The project retains the upstream MIT license and attribution; see [LICENSE](LICENSE) and [THIRD-PARTY-NOTICES.md](THIRD-PARTY-NOTICES.md).

Upstream PvP fixes: [TheobaldTheBird/CarpetPVP 1.2.0 source](https://github.com/TheobaldTheBird/CarpetPVP/tree/1.21). Base project: [gnembon/fabric-carpet](https://github.com/gnembon/fabric-carpet).
Backport source and issue tracker: [JustHari01/Carpet-PVP-1.20.1-Fabric-Backport](https://github.com/JustHari01/Carpet-PVP-1.20.1-Fabric-Backport).

## Testing status

The source compiled and remapped locally. No Minecraft client or dedicated server was launched, so in-game behavior and stability have not been verified.

Minecraft 1.20.1 does not have the upstream `LivingEntity.getKnockback(...)` hook. The backport applies the same hurt-resistance check at the corresponding `Player.attack` knockback call after `hurt()` has processed the target. This skips only the extra attacker knockback for the primary target; Minecraft's damage knockback and the separate sweep-target path are left intact. The adaptation is compile-checked but has not yet been confirmed in a launched game.

AI disclosure: AI tools assisted with adapting the listed Carpet PVP behavior to the 1.20.1 source. The original Carpet and Carpet PVP authors are credited above.
