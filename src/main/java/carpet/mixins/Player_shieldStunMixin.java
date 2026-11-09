package carpet.mixins;

import carpet.CarpetSettings;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class Player_shieldStunMixin extends LivingEntity
{
    protected Player_shieldStunMixin(EntityType<? extends LivingEntity> entityType, Level level)
    {
        super(entityType, level);
    }

    @Inject(method = "blockUsingShield", at = @At("HEAD"))
    private void carpetpvp$resetInvulnerabilityAfterShieldBreak(LivingEntity attacker, CallbackInfo ci)
    {
        if (!CarpetSettings.shieldStunning || !attacker.canDisableShield()) return;
        if (this.level() instanceof ServerLevel serverLevel)
        {
            serverLevel.getServer().tell(new TickTask(serverLevel.getServer().getTickCount(), () -> this.invulnerableTime = 0));
        }
    }
}
