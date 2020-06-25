package io.github.tastac.dungeonsmod.client.particle;

import io.github.tastac.dungeonsmod.DungeonsMod;
import io.github.tastac.dungeonsmod.common.particles.SoulParticleData;
import net.minecraft.client.particle.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author CoffeeCatRailway
 * Created: 25/06/2020
 */
@OnlyIn(Dist.CLIENT)
public class SoulParticle extends SimpleAnimatedParticle {

    private PlayerEntity player;

    private SoulParticle(World world, double x, double y, double z, IAnimatedSprite sprite, PlayerEntity player) {
        super(world, x + offset(world), y + offset(world), z + offset(world), sprite, -5.0E-4F);
        this.motionX = 0d;
        this.motionY = 0d;
        this.motionZ = 0d;
        this.particleScale *= 0.75F;
        this.maxAge = 60 + this.rand.nextInt(12);
        this.setColorFade(0xf2dec9);
        this.selectSpriteWithAge(sprite);
        this.player = player;
    }

    @Override
    public void tick() {
        super.tick();
        float motionSpeed = DungeonsMod.SERVER_CONFIG.soulsParticleMotionSpeed.get().floatValue();
        Vec3d pos = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d playerPos = this.player.getPositionVec().add(0f, 1f, 0f);
        Vec3d motion = pos.subtract(playerPos).normalize().mul(motionSpeed, motionSpeed, motionSpeed).inverse();
        this.motionX = motion.x;
        this.motionY = motion.y;
        this.motionZ = motion.z;

        if (pos.squareDistanceTo(playerPos) <= DungeonsMod.SERVER_CONFIG.soulsParticleExpireDist.get() * DungeonsMod.SERVER_CONFIG.soulsParticleExpireDist.get())
            this.setExpired();
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    private static double offset(World world) {
        return -.5d + world.rand.nextDouble() * (.5d - -.5d);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<SoulParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite p_i50058_1_) {
            this.spriteSet = p_i50058_1_;
        }

        @Override
        public Particle makeParticle(SoulParticleData type, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SoulParticle(world, x, y, z, this.spriteSet, type.getPlayer());
        }
    }
}