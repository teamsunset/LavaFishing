package com.sunset.lavafishing.item;

import com.sunset.lavafishing.util.Reference;
import com.sunset.lavafishing.util.RegistryCollection.MobEffectCollection;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;

import javax.annotation.Nonnull;

public class PromethiumArmor extends ArmorItem
{
    //    private static final AttributeModifier INCREASED_SWIM_SPEED = new AttributeModifier(UUID.fromString("d820cadc-2d19-421c-b19f-4c1f5b84a418"), "Neptunium Boots swim speed boost", 0.5D, AttributeModifier.Operation.ADDITION);
    private String texture;

    public PromethiumArmor(ArmorMaterial armorMaterial, ArmorItem.Type type) {
        super(armorMaterial, type, new Properties()
                .fireResistant());
    }

//    @Override
//    public void onArmorTick(@Nonnull ItemStack stack, Level world, Player player) {
//        if (player.isOnFire()) {
//            if (this.type == Type.CHESTPLATE) {
//                player.addEffect(new MobEffectInstance(MobEffects.HEAL, 10, 0, false, false, false));
//            }
//        }
//        if (player.isInLava() || world.getBlockState(new BlockPos(player.getBlockX(), player.getBlockY() - 1, player.getBlockZ())).is(Blocks.LAVA)) {
//            if (this.type == Type.LEGGINGS) {
//                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 0, false, false, false));
//            } else if (this.type == Type.BOOTS) {
//                player.addEffect(new MobEffectInstance(MobEffectCollection.EFFECT_LAVA_WALKER.get(), 20, 0, false, false, false));
//            }
//        }
//    }

//    @Override
//    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
//        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
//        if (stack.getItem() instanceof PromethiumArmor item) {
//            if (player.isOnFire()) {
//                if (item.type == Type.CHESTPLATE) {
//                    player.addEffect(new MobEffectInstance(MobEffects.HEAL, 10, 0, false, false, false));
//                }
//            }
//            if (player.isInLava() || level.getBlockState(new BlockPos(player.getBlockX(), player.getBlockY() - 1, player.getBlockZ())).is(Blocks.LAVA)) {
//                if (item.type == Type.LEGGINGS) {
//                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 0, false, false, false));
//                } else if (item.type == Type.BOOTS) {
//                    player.addEffect(new MobEffectInstance(MobEffectCollection.EFFECT_LAVA_WALKER.get(), 20, 0, false, false, false));
//                }
//            }
//        }
//    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level world = player.level();
        for (ItemStack itemStack : player.getArmorSlots()) {
            if (itemStack.getItem() instanceof PromethiumArmor item) {
                if (player.isOnFire()) {
                    if (item.type == Type.CHESTPLATE) {
                        player.heal(0.1f);
                    }
                }
                if (player.isInLava() || world.getBlockState(player.getOnPos()).is(Blocks.LAVA)) {
                    if (item.type == Type.LEGGINGS) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 0, false, false, false));
                    } else if (item.type == Type.BOOTS) {
                        MobEffect lavaWalker = MobEffectCollection.EFFECT_LAVA_WALKER.get();
                        player.addEffect(new MobEffectInstance(MobEffectCollection.EFFECT_LAVA_WALKER.get(), 20, 0, false, false, false));
                    }
                }
            }
        }
    }

    public Item setArmorTexture(String string) {
        this.texture = string;
        return this;
    }

    @Override
    public String getArmorTexture(@Nonnull ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
        return Reference.MOD_ID + ":textures/armor/" + this.texture + ".png";
    }
}
