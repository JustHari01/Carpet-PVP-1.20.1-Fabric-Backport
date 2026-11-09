package carpet.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class Player_attackKnockbackFixMixin
{
    /**
     * Minecraft 1.20.1 applies the attacker's extra knockback from Player.attack,
     * while the upstream 1.21 code gates that value through LivingEntity.getKnockback.
     * At this first knockback call the target has already processed hurt(), so a
     * value below 20 identifies a hit accepted during the hurt-resistance window.
     */
    @Redirect(
            method = "attack(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V",
                    ordinal = 0
            )
    )
    private void carpetPvp$skipExtraKnockbackDuringHurtResistance(
            LivingEntity target, double strength, double x, double z)
    {
        if (target.invulnerableTime < 20)
        {
            return;
        }
        target.knockback(strength, x, z);
    }
}
