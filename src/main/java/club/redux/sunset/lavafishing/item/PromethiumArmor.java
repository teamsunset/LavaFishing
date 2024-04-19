package club.redux.sunset.lavafishing.item;

import club.asynclab.web.BuildConstants;
import club.redux.sunset.lavafishing.registry.RegistryMobEffect;
import net.minecraft.MethodsReturnNonnullByDefault;
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

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PromethiumArmor extends ArmorItem {
    //    private static final AttributeModifier INCREASED_SWIM_SPEED = new AttributeModifier(UUID.fromString("d820cadc-2d19-421c-b19f-4c1f5b84a418"), "Neptunium Boots swim speed boost", 0.5D, AttributeModifier.Operation.ADDITION);
    private String texture;

    public PromethiumArmor(ArmorMaterial armorMaterial, ArmorItem.Type type) {
        super(armorMaterial, type, new Properties().fireResistant());
    }

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
                        MobEffect lavaWalker = RegistryMobEffect.LAVA_WALKER.get();
                        player.addEffect(new MobEffectInstance(RegistryMobEffect.LAVA_WALKER.get(), 20, 0, false, false, false));
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
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
        return BuildConstants.MOD_ID + ":textures/armor/" + this.texture + ".png";
    }
}
