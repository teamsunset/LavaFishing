package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.entity.EntityObsidianHook
import club.redux.sunset.lavafishing.registry.RegistryItem
import club.redux.sunset.lavafishing.util.isServerSide
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.FishingRodItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

class ItemObsidianFishingRod : FishingRodItem(Properties().durability(128)) {
    /**
     * # 处理玩家使用物品的逻辑</h1>
     *
     * 抄原版的
     *
     * @param pLevel 玩家当前所在的世界
     * @param pPlayer 当前进行交互的玩家
     * @param pHand 玩家使用的手（主手或副手）
     * @return 返回一个包含交互结果的InteractionResultHolder对象，其中包含了交互成功与否以及交互后的物品堆栈。
     */
    override fun use(pLevel: Level, pPlayer: Player, pHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemStack = pPlayer.getItemInHand(pHand) // 获取玩家手中持有的物品堆栈
        if (pPlayer.fishing != null) { // 检查玩家是否正在进行钓鱼
            if (pLevel.isServerSide()) { // 如果是在服务器端，则处理钓竿的磨损和物品破坏逻辑
                val damage = pPlayer.fishing!!.retrieve(itemStack) // 从钓鱼活动中获取对物品的损伤值
                itemStack.hurtAndBreak(damage, pPlayer) { player ->
                    player.broadcastBreakEvent(pHand) // 对物品造成损伤并若物品破坏，则广播破坏事件
                }
            }
            pPlayer.swing(pHand) // 触发玩家的挥动动画
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH, pPlayer) // 触发物品交互结束的遊戲事件
        } else {
            this.playThrowSound(pPlayer, pLevel) // 如果玩家没有在钓鱼，则播放投掷声音
            if (pLevel.isServerSide()) { // 如果是在服务器端，则生成一个黑曜石钩子实体
                pLevel.addFreshEntity(EntityObsidianHook(pPlayer, pLevel, 0, 0))
            }
            pPlayer.swing(pHand) // 触发玩家的挥动动画
            pPlayer.awardStat(Stats.ITEM_USED[this]) // 给玩家奖励使用该物品的统计
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START, pPlayer) // 触发物品交互开始的遊戲事件
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide()) // 根据事件发生的客户端或服务器端，返回交互结果
    }

    private fun playThrowSound(pPlayer: Player, pLevel: Level) {
        pLevel.playSound(
            null,
            pPlayer.x,
            pPlayer.y,
            pPlayer.z,
            SoundEvents.FISHING_BOBBER_THROW,
            SoundSource.NEUTRAL,
            0.5f,
            0.4f / (pLevel.getRandom().nextFloat() * 0.4f + 0.8f)
        )
    }

    companion object {
        @JvmStatic
        fun setupClient(event: FMLClientSetupEvent) {
            event.enqueueWork {
                ItemProperties.register(
                    RegistryItem.OBSIDIAN_FISHING_ROD.get(),
                    ResourceLocation("cast")
                ) { pStack, pLevel, pEntity, pSeed ->
                    if (
                        pEntity is Player &&
                        pEntity.fishing != null &&
                        (pEntity.getMainHandItem() == pStack || pEntity.getOffhandItem() == pStack)
                    ) {
                        1.0f
                    } else {
                        0f
                    }
                }
            }
        }
    }
}
