package com.sunset.item;

import com.sunset.creativetab.TabLavaFishing;
import com.sunset.entity.EntityObsidianHook;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemObsidianFishingRod extends FishingRodItem
{
    public ItemObsidianFishingRod() {
        super(GetProperties());
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.fishing != null) {
            if (!pLevel.isClientSide) {
                int damage = pPlayer.fishing.retrieve(itemstack);
                itemstack.hurtAndBreak(damage, pPlayer, (p_41288_) -> {
                    p_41288_.broadcastBreakEvent(pHand);
                });
            }
            pPlayer.swing(pHand);
            pLevel.gameEvent(pPlayer, GameEvent.FISHING_ROD_REEL_IN, pPlayer);
        }
        else {
            playThrowSound(pPlayer, pLevel);
            if (!pLevel.isClientSide) {
                EntityObsidianHook entityFishHook = new EntityObsidianHook(pPlayer, pLevel, 0, 0);
                pLevel.addFreshEntity(entityFishHook);
            }
            pPlayer.swing(pHand);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pLevel.gameEvent(pPlayer, GameEvent.FISHING_ROD_CAST, pPlayer);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    public static Properties GetProperties() {
        return new Properties()
                .tab(TabLavaFishing.TAB_LAVA_FISHING)
                .durability(128);
    }

    private static void playThrowSound(Player pPlayer, Level pLevel) {
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
    }

}
