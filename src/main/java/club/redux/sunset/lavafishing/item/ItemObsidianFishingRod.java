package club.redux.sunset.lavafishing.item;

import club.redux.sunset.lavafishing.entity.EntityObsidianHook;
import club.redux.sunset.lavafishing.util.RegistryCollection.ItemCollection;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemObsidianFishingRod extends FishingRodItem {
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
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH, pPlayer);
        } else {
            playThrowSound(pPlayer, pLevel);
            if (!pLevel.isClientSide) {
                EntityObsidianHook entityFishHook = new EntityObsidianHook(pPlayer, pLevel, 0, 0);
                pLevel.addFreshEntity(entityFishHook);
            }
            pPlayer.swing(pHand);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START, pPlayer);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    public static Properties GetProperties() {
        return new Properties()
                .durability(128);
    }

    private static void playThrowSound(Player pPlayer, Level pLevel) {
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ItemCollection.ITEM_OBSIDIAN_FISHING_ROD.get(), new ResourceLocation("cast"), (pStack, pLevel, pEntity, pSeed) -> {
                if (pEntity instanceof Player player) {
                    if (player.getMainHandItem() == pStack) {
                        if (player.fishing != null)
                            return 1.0f;
                    } else if (player.getOffhandItem() == pStack) {
                        if (player.fishing != null)
                            return 1.0f;
                    }
                }
                return 0;
            });
        });
    }
}
