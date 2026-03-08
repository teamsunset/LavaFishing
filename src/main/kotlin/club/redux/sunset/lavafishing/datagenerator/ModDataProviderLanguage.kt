package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.registry.ModBlocks
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModItemsAqua
import club.redux.sunset.lavafishing.registry.ModMobEffects
import club.redux.sunset.lavafishing.tool.datagenerator.DataProviderLanguage
import com.teammetallurgy.aquaculture.Aquaculture
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Item
import java.util.*

class ModDataProviderLanguage(
    packOutput: PackOutput,
    private val locale: Locale,
) : DataProviderLanguage(packOutput, BuiltConstants.MOD_ID, locale) {

    override fun addTranslations() {
        // MobEffect
        ModMobEffects.ENDLESS_FLAME.get().addTranslation(
            Locale.PRC to "无尽之火",
            Locale.US to "Endless Flame"
        )
        ModMobEffects.LAVA_WALKER.get().addTranslation(
            Locale.PRC to "熔岩行者",
            Locale.US to "Lava Walker"
        )

        // Potion
        "item.minecraft.potion.effect.lava_walker".addTranslation(
            Locale.PRC to "熔岩行者药水",
            Locale.US to "Potion of Lava Walker"
        )
        "item.minecraft.splash_potion.effect.lava_walker".addTranslation(
            Locale.PRC to "喷溅型熔岩行者药水",
            Locale.US to "Splash Potion of Lava Walker"
        )
        "item.minecraft.lingering_potion.effect.lava_walker".addTranslation(
            Locale.PRC to "滞留型熔岩行者药水",
            Locale.US to "Lingering Potion of Lava Walker"
        )

        // Fish
        val fishMap = mapOf(
            ModItems.AGNI_FISH.get() to arrayOf(
                Locale.PRC to "火神鱼",
                Locale.US to "Agni Fish"
            ),
            ModItems.AROWANA_FISH.get() to arrayOf(
                Locale.PRC to "龙鱼",
                Locale.US to "Arowana Fish"
            ),
            ModItems.SCALY_FOOT_SNAIL.get() to arrayOf(
                Locale.PRC to "鳞足蜗牛",
                Locale.US to "Scaly Foot Snail"
            ),
            ModItems.YETI_CRAB.get() to arrayOf(
                Locale.PRC to "雪人蟹",
                Locale.US to "Yeti Crab"
            ),
            ModItems.LAVA_LAMPREY.get() to arrayOf(
                Locale.PRC to "熔岩七鳃鳗",
                Locale.US to "Lava Lamprey"
            ),
            ModItems.FLAME_SQUAT_LOBSTER.get() to arrayOf(
                Locale.PRC to "烈焰铠甲虾",
                Locale.US to "Flame Squat Lobster"
            ),
            ModItems.QUARTZ_FISH.get() to arrayOf(
                Locale.PRC to "石英鱼",
                Locale.US to "Quartz Fish"
            ),
            ModItems.OBSIDIAN_SWORD_FISH.get() to arrayOf(
                Locale.PRC to "黑曜石剑鱼",
                Locale.US to "Obsidian Sword Fish"
            ),
            ModItems.STEAM_FLYING_FISH.get() to arrayOf(
                Locale.PRC to "蒸汽飞鱼",
                Locale.US to "Steam Flying Fish"
            ),
        )
        fishMap.forEach { (fish, pairs) -> fish.addTranslation(*pairs) }
        fishMap.forEach { (fish, pairs) ->
            "${fish.descriptionId}_bucket".addTranslation(
                this.locale to this.match(*pairs) + this.match(
                    Locale.PRC to "桶",
                    Locale.US to " Bucket"
                )
            )
        }
        fishMap.forEach { (fish, pairs) ->
            val location = BuiltInRegistries.ITEM.getKey(fish)
            "entity.${location.namespace}.${location.path}".addTranslation(*pairs)
        }

        // Tool
        ModItems.OBSIDIAN_FISHING_ROD.get().addTranslation(
            Locale.PRC to "黑曜石鱼竿",
            Locale.US to "Obsidian Fishing Rod"
        )
        ModItems.NETHERITE_FISHING_ROD.get().addTranslation(
            Locale.PRC to "下界合金鱼竿",
            Locale.US to "Netherite Fishing Rod"
        )

        ModItems.HYDROTHERMAL_HOOK.get().addTranslation(
            Locale.PRC to "热液鱼钩",
            Locale.US to "Hydrothermal Hook"
        )

        // Armor
        ModItems.PROMETHIUM_BOOTS.get().addTranslation(
            Locale.PRC to "钷靴子",
            Locale.US to "Promethium Boots"
        )
        ModItems.PROMETHIUM_CHESTPLATE.get().addTranslation(
            Locale.PRC to "钷胸甲",
            Locale.US to "Promethium Chestplate"
        )
        ModItems.PROMETHIUM_LEGGINGS.get().addTranslation(
            Locale.PRC to "钷护腿",
            Locale.US to "Promethium Leggings"
        )
        ModItems.PROMETHIUM_HELMET.get().addTranslation(
            Locale.PRC to "钷头盔",
            Locale.US to "Promethium Helmet"
        )

        // Slingshot
        ModItems.IRON_SLINGSHOT.get().addTranslation(
            Locale.PRC to "铁弹弓",
            Locale.US to "Iron Slingshot"
        )
        ModItems.NEPTUNIUM_SLINGSHOT.get().addTranslation(
            Locale.PRC to "镎弹弓",
            Locale.US to "Neptunium Slingshot"
        )
        ModItems.PROMETHIUM_SLINGSHOT.get().addTranslation(
            Locale.PRC to "钷弹弓",
            Locale.US to "Promethium Slingshot"
        )

        // Bullet
        val bulletMap = mapOf(
            ModItems.STONE_BULLET.get() to arrayOf(
                Locale.PRC to "石弹丸",
                Locale.US to "Stone Bullet"
            ),
            ModItems.IRON_BULLET.get() to arrayOf(
                Locale.PRC to "铁弹丸",
                Locale.US to "Iron Bullet"
            ),
            ModItems.NEPTUNIUM_BULLET.get() to arrayOf(
                Locale.PRC to "镎弹丸",
                Locale.US to "Neptunium Bullet"
            ),
            ModItems.PROMETHIUM_BULLET.get() to arrayOf(
                Locale.PRC to "钷弹丸",
                Locale.US to "Promethium Bullet"
            )
        )
        bulletMap.forEach { (bullet, pairs) -> bullet.addTranslation(*pairs) }
        bulletMap.forEach { (bullet, pairs) ->
            val location = BuiltInRegistries.ITEM.getKey(bullet)
            "entity.${location.namespace}.${location.path}".addTranslation(*pairs)
        }

        // Material
        ModItems.PROMETHIUM_INGOT.get().addTranslation(
            Locale.PRC to "钷锭",
            Locale.US to "Promethium Ingot"
        )
        ModItems.PROMETHIUM_NUGGET.get().addTranslation(
            Locale.PRC to "钷粒",
            Locale.US to "Promethium Nugget"
        )

        // Block
        ModBlocks.PROMETHEUS_BOUNTY.get().addTranslation(
            Locale.PRC to "普罗米修斯的恩惠",
            Locale.US to "Prometheus Bounty"
        )
        ModBlocks.PROMETHIUM_BLOCK.get().addTranslation(
            Locale.PRC to "钷块",
            Locale.US to "Promethium Block"
        )

        // Food
        ModItems.SPICY_FISH_FILLET.get().addTranslation(
            Locale.PRC to "麻辣鱼片",
            Locale.US to "Spicy Fish Fillet"
        )
        ModItems.FISH_PASTE.get().addTranslation(
            Locale.PRC to "鱼糜",
            Locale.US to "Fish Paste"
        )

        //Sound
        "sounds.lavafishing.item.slingshot".addTranslation(
            Locale.PRC to "弹弓",
            Locale.US to "Slingshot"
        )

        // Creative Tab
        "itemGroup.lavafishing".addTranslation(
            Locale.PRC to "熔岩钓鱼",
            Locale.US to "Lava Fishing"
        )

        // Message
        "message.${BuiltConstants.MOD_ID}.hook.invalid_fluid".addTranslation(
            Locale.PRC to "请使用适用于%s的鱼钩",
            Locale.US to "Use a hook suited for %s"
        )
        "message.${BuiltConstants.MOD_ID}.hook.invalid_fluid_generic".addTranslation(
            Locale.PRC to "请使用正确类型的鱼钩",
            Locale.US to "Use the correct type of hook"
        )

        // Tooltip
        class Tooltip(val item: Item) {
            private var modId = Aquaculture.MOD_ID
            private var titleCount = 0
            private var descCount = 0

            fun modId(modId: String): Tooltip = this.apply { this.modId = modId }
            fun title(vararg pairs: Pair<Locale, String>): Tooltip = addTranslation(pairs, "title")
            fun desc(vararg pairs: Pair<Locale, String>): Tooltip = addTranslation(pairs, "desc")
            private fun addTranslation(pairs: Array<out Pair<Locale, String>>, type: String): Tooltip {
                val count = if (type == "title") titleCount++ else descCount++
                val itemLocation = BuiltInRegistries.ITEM.getKey(item)
                val key = "${modId}.${itemLocation.path}.tooltip.$type${if (count == 0) "" else ".$count"}"
                key.addTranslation(*pairs)
                return this
            }
        }

        val heatLoverTitle = arrayOf(
            Locale.PRC to "喜热",
            Locale.US to "Heat Lover"
        )
        val heatLoverDesc = arrayOf(
            Locale.PRC to "25% 火焰伤害减免",
            Locale.US to "25% fire damage reduction"
        )

        Tooltip(ModItems.NEPTUNIUM_BULLET.get()).modId(BuiltConstants.MOD_ID)
            .title(
                Locale.PRC to "海王突击",
                Locale.US to "Neptune's Strike"
            )
            .desc(
                Locale.PRC to "能顺畅地通过水体",
                Locale.US to "Can pass smoothly through water"
            )
            .title(
                Locale.PRC to "海洋公敌",
                Locale.US to "Enemy of the sea"
            )
            .desc(
                Locale.PRC to "追踪所有种类的水生生物",
                Locale.US to "Track all kinds of aquatic life"
            )
        Tooltip(ModItems.NEPTUNIUM_SLINGSHOT.get()).modId(BuiltConstants.MOD_ID)
            .title(
                Locale.PRC to "海王突击",
                Locale.US to "Neptune's Strike"
            )
            .desc(
                Locale.PRC to "使弹丸能顺畅通过水体",
                Locale.US to "Makes bullets go smoothly through water"
            )
        Tooltip(ModItems.PROMETHIUM_BULLET.get()).modId(BuiltConstants.MOD_ID)
            .title(
                Locale.PRC to "祸害",
                Locale.US to "Disaster"
            )
            .desc(
                Locale.PRC to "分裂爆炸弹丸",
                Locale.US to "Split explosive bullets"
            )
        Tooltip(ModItems.PROMETHIUM_SLINGSHOT.get()).modId(BuiltConstants.MOD_ID)
            .title(
                Locale.PRC to "过热",
                Locale.US to "Overheating"
            )
            .desc(
                Locale.PRC to "连发",
                Locale.US to "Repeating"
            )
        Tooltip(ModItems.HYDROTHERMAL_HOOK.get()).modId(BuiltConstants.MOD_ID)
            .title(
                Locale.PRC to "通用",
                Locale.US to "Universal"
            )
            .desc(
                Locale.PRC to "可在水和岩浆中垂钓",
                Locale.US to "Can fish in both water and lava"
            )
        Tooltip(ModItems.PROMETHIUM_HELMET.get()).modId(BuiltConstants.MOD_ID)
            .title(*heatLoverTitle).desc(*heatLoverDesc)
            .title(
                Locale.PRC to "金睛",
                Locale.US to "Golden Eyes"
            )
            .desc(
                Locale.PRC to "在熔岩下和着火时拥有良好视野",
                Locale.US to "Watch under the lava and on fire clearly"
            )
        Tooltip(ModItems.PROMETHIUM_CHESTPLATE.get()).modId(BuiltConstants.MOD_ID)
            .title(*heatLoverTitle).desc(*heatLoverDesc)
            .title(
                Locale.PRC to "焚身",
                Locale.US to "Immolator"
            )
            .desc(
                Locale.PRC to "在炎热时缓慢恢复生命",
                Locale.US to "Slowly regenerates health in the heat"
            )
        Tooltip(ModItems.PROMETHIUM_LEGGINGS.get()).modId(BuiltConstants.MOD_ID)
            .title(*heatLoverTitle).desc(*heatLoverDesc)
            .title(
                Locale.PRC to "幽步",
                Locale.US to "Ghost"
            )
            .desc(
                Locale.PRC to "提高炎热时的移动速度",
                Locale.US to "Increases movement speed in the heat"
            )
        Tooltip(ModItems.PROMETHIUM_BOOTS.get()).modId(BuiltConstants.MOD_ID)
            .title(*heatLoverTitle).desc(*heatLoverDesc)
            .title(
                Locale.PRC to "蒸汽",
                Locale.US to "Steam"
            )
            .desc(
                Locale.PRC to "在熔岩上行走",
                Locale.US to "Walk on the lava"
            )
    }
}
