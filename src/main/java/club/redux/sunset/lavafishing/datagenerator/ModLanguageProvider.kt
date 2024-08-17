package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.registry.ModBlocks
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModMobEffects
import net.minecraft.data.PackOutput
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.data.LanguageProvider
import java.util.*

class ModLanguageProvider(
    packOutput: PackOutput,
    private val locale: Locale,
) : LanguageProvider(packOutput, BuildConstants.MOD_ID, locale.toString().lowercase()) {
    private fun choose(vararg pairs: Pair<Locale, String>): String =
        pairs.find { it.first == this.locale }?.second ?: throw IllegalArgumentException("Locale not found")

    private fun Item.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    private fun Block.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    private fun EntityType<*>.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    private fun String.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    private fun ItemStack.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))
    private fun MobEffect.addToTranslation(vararg pairs: Pair<Locale, String>) = add(this, choose(*pairs))

//    inner class PlaceHolder(private vararg val pairs: Pair<Locale, String>) {
//        override fun toString(): String {
//            return choose(*pairs)
//        }
//    }
//
//    val fish = PlaceHolder(
//        Locale.PRC to "鱼",
//        Locale.US to "Fish"
//    )

    override fun addTranslations() {
        // MobEffect
        ModMobEffects.ENDLESS_FLAME.get().addToTranslation(
            Locale.PRC to "无尽之火",
            Locale.US to "Endless Flame"
        )
        ModMobEffects.LAVA_WALKER.get().addToTranslation(
            Locale.PRC to "岩浆行者",
            Locale.US to "Lava Walker"
        )

        // Potion
        "item.minecraft.potion.effect.lava_walker".addToTranslation(
            Locale.PRC to "岩浆行者药水",
            Locale.US to "Potion of Lava Walker"
        )
        "item.minecraft.splash_potion.effect.lava_walker".addToTranslation(
            Locale.PRC to "喷溅型岩浆行者药水",
            Locale.US to "Splash Potion of Lava Walker"
        )
        "item.minecraft.lingering_potion.effect.lava_walker".addToTranslation(
            Locale.PRC to "滞留型岩浆行者药水",
            Locale.US to "Lingering Potion of Lava Walker"
        )

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
            )
        )

        // Fish
        fishMap.forEach { (fish, pairs) -> fish.addToTranslation(*pairs) }

        // FishBucket
        fishMap.forEach { (fish, pairs) ->
            "${fish.descriptionId}_bucket".addToTranslation(
                this.locale to choose(*pairs) + choose(
                    Locale.PRC to "桶",
                    Locale.US to " Bucket"
                )
            )
        }

        // FishEntity
        fishMap.forEach { (fish, pairs) ->
            "entity${fish.descriptionId.substring(fish.descriptionId.indexOf('.'))}".addToTranslation(*pairs)
        }


        // Tool
        ModItems.OBSIDIAN_FISHING_ROD.get().addToTranslation(
            Locale.PRC to "黑曜石鱼竿",
            Locale.US to "Obsidian Fishing Rod"
        )

        ModItems.PROMETHIUM_BOOTS.get().addToTranslation(
            Locale.PRC to "钷靴子",
            Locale.US to "Promethium Boots"
        )
        ModItems.PROMETHIUM_CHESTPLATE.get().addToTranslation(
            Locale.PRC to "钷胸甲",
            Locale.US to "Promethium Chestplate"
        )
        ModItems.PROMETHIUM_HELMET.get().addToTranslation(
            Locale.PRC to "钷头盔",
            Locale.US to "Promethium Helmet"
        )

        // Slingshot
        ModItems.NEPTUNIUM_SLINGSHOT.get().addToTranslation(
            Locale.PRC to "镎弹弓",
            Locale.US to "Neptunium Slingshot"
        )
        ModItems.PROMETHIUM_SLINGSHOT.get().addToTranslation(
            Locale.PRC to "钷弹弓",
            Locale.US to "Promethium Slingshot"
        )
        ModItems.STONE_BULLET.get().addToTranslation(
            Locale.PRC to "石弹丸",
            Locale.US to "Stone Bullet"
        )
        ModItems.IRON_BULLET.get().addToTranslation(
            Locale.PRC to "铁弹丸",
            Locale.US to "Iron Bullet"
        )
        ModItems.NEPTUNIUM_BULLET.get().addToTranslation(
            Locale.PRC to "镎弹丸",
            Locale.US to "Neptunium Bullet"
        )
        ModItems.PROMETHIUM_BULLET.get().addToTranslation(
            Locale.PRC to "钷弹丸",
            Locale.US to "Promethium Bullet"
        )

        // Material
        ModItems.PROMETHIUM_INGOT.get().addToTranslation(
            Locale.PRC to "钷锭",
            Locale.US to "Promethium Ingot"
        )
        ModItems.PROMETHIUM_NUGGET.get().addToTranslation(
            Locale.PRC to "钷粒",
            Locale.US to "Promethium Nugget"
        )

        // Block
        ModBlocks.PROMETHEUS_BOUNTY.get().addToTranslation(
            Locale.PRC to "普罗米修斯的恩惠",
            Locale.US to "Prometheus Bounty"
        )
        ModBlocks.PROMETHIUM_BLOCK.get().addToTranslation(
            Locale.PRC to "钷块",
            Locale.US to "Promethium Block"
        )

        // Food
        ModItems.SPICY_FISH_FILLET.get().addToTranslation(
            Locale.PRC to "麻辣鱼片",
            Locale.US to "Spicy Fish Fillet"
        )

        //Sound
        "sounds.lavafishing.item.slingshot".addToTranslation(
            Locale.PRC to "弹弓",
            Locale.US to "Slingshot"
        )

        // Creative Tab
        "itemGroup.lavafishing".addToTranslation(
            Locale.PRC to "岩浆钓鱼",
            Locale.US to "Lava Fishing"
        )

        // Tooltip
        val heatLoverTitle = arrayOf(
            Locale.PRC to "喜热",
            Locale.US to "Heat Lover"
        )
        val heatLoverDesc = arrayOf(
            Locale.PRC to "25% 火焰伤害减免",
            Locale.US to "25% fire damage reduction"
        )

        "aquaculture.neptunium_bullet.tooltip.title".addToTranslation(
            Locale.PRC to "海洋公敌",
            Locale.US to "Enemy of the sea"
        )
        "aquaculture.neptunium_bullet.tooltip.desc".addToTranslation(
            Locale.PRC to "追踪所有种类的水生生物",
            Locale.US to "Track all kinds of aquatic life"
        )
        "aquaculture.neptunium_slingshot.tooltip.title".addToTranslation(
            Locale.PRC to "海王突击",
            Locale.US to "Neptune's Strike"
        )
        "aquaculture.neptunium_slingshot.tooltip.desc".addToTranslation(
            Locale.PRC to "使弹丸能顺畅通过水体",
            Locale.US to "Makes bullets go smoothly through water"
        )
        "lavafishing.promethium_bullet.tooltip.title".addToTranslation(
            Locale.PRC to "祸害",
            Locale.US to "Disaster"
        )
        "lavafishing.promethium_bullet.tooltip.desc".addToTranslation(
            Locale.PRC to "分裂爆炸弹丸",
            Locale.US to "Split explosive bullets"
        )
        "lavafishing.promethium_slingshot.tooltip.title".addToTranslation(
            Locale.PRC to "过热",
            Locale.US to "Overheating"
        )
        "lavafishing.promethium_slingshot.tooltip.desc".addToTranslation(
            Locale.PRC to "连发",
            Locale.US to "Repeating"
        )
        "lavafishing.double_obsidian_hook.tooltip.title".addToTranslation(
            Locale.PRC to "双钩",
            Locale.US to "Double Barb"
        )
        "lavafishing.double_obsidian_hook.tooltip.desc".addToTranslation(
            Locale.PRC to "有几率钓到两个东西",
            Locale.US to "Chance to catch two things"
        )
        "lavafishing.glowstone_hook.tooltip.title".addToTranslation(
            Locale.PRC to "幸运",
            Locale.US to "Lucky"
        )
        "lavafishing.glowstone_hook.tooltip.desc".addToTranslation(
            Locale.PRC to "提升运气",
            Locale.US to "Increased luck"
        )
        "lavafishing.quartz_hook.tooltip.title".addToTranslation(
            Locale.PRC to "耐用",
            Locale.US to "Durable"
        )
        "lavafishing.quartz_hook.tooltip.desc".addToTranslation(
            Locale.PRC to "30% 几率不消耗耐久度",
            Locale.US to "30% chance to not use durability"
        )
        "lavafishing.soul_sand_hook.tooltip.title".addToTranslation(
            Locale.PRC to "诱惑",
            Locale.US to "Enticing"
        )
        "lavafishing.soul_sand_hook.tooltip.desc".addToTranslation(
            Locale.PRC to "延长钓上鱼所需的时间",
            Locale.US to "Increases how long you have to reel in fish"
        )
        "lavafishing.obsidian_note_hook.tooltip.title".addToTranslation(
            Locale.PRC to "警报",
            Locale.US to "Alert"
        )
        "lavafishing.obsidian_note_hook.tooltip.desc".addToTranslation(
            Locale.PRC to "当鱼接近时发出警报声",
            Locale.US to "Plays an alert when a fish is approaching"
        )
        "lavafishing.promethium_helmet.tooltip.title.1".addToTranslation(
            *heatLoverTitle
        )
        "lavafishing.promethium_helmet.tooltip.title".addToTranslation(
            Locale.PRC to "金睛",
            Locale.US to "Golden Eyes"
        )
        "lavafishing.promethium_helmet.tooltip.desc.1".addToTranslation(
            *heatLoverDesc
        )
        "lavafishing.promethium_helmet.tooltip.desc".addToTranslation(
            Locale.PRC to "在岩浆下拥有良好视野",
            Locale.US to "Watch under the lava clearly"
        )
        "lavafishing.promethium_chestplate.tooltip.title.1".addToTranslation(
            *heatLoverTitle
        )
        "lavafishing.promethium_chestplate.tooltip.title".addToTranslation(
            Locale.PRC to "焚身",
            Locale.US to "Immolator"
        )
        "lavafishing.promethium_chestplate.tooltip.desc.1".addToTranslation(
            *heatLoverDesc
        )
        "lavafishing.promethium_chestplate.tooltip.desc".addToTranslation(
            Locale.PRC to "在炎热时缓慢恢复生命",
            Locale.US to "Slowly regenerates health in the heat"
        )
        "lavafishing.promethium_leggings.tooltip.title.1".addToTranslation(
            *heatLoverTitle
        )
        "lavafishing.promethium_leggings.tooltip.title".addToTranslation(
            Locale.PRC to "幽步",
            Locale.US to "Ghost"
        )
        "lavafishing.promethium_leggings.tooltip.desc.1".addToTranslation(
            *heatLoverDesc
        )
        "lavafishing.promethium_leggings.tooltip.desc".addToTranslation(
            Locale.PRC to "提高炎热时的移动速度",
            Locale.US to "Increases movement speed in the heat"
        )
        "lavafishing.promethium_boots.tooltip.title.1".addToTranslation(
            *heatLoverTitle
        )
        "lavafishing.promethium_boots.tooltip.title".addToTranslation(
            Locale.PRC to "蒸汽",
            Locale.US to "Steam"
        )
        "lavafishing.promethium_boots.tooltip.desc.1".addToTranslation(
            *heatLoverDesc
        )
        "lavafishing.promethium_boots.tooltip.desc".addToTranslation(
            Locale.PRC to "在岩浆上行走",
            Locale.US to "Walk on the lava"
        )
    }
}

//{
//    "effect.lavafishing.blessed": "Blessed",
//    "effect.lavafishing.endless_flame": "Endless Flame",
//    "effect.lavafishing.lava_walker": "Lava Walker",
//    "item.lavafishing.agni_fish": "Agni Fish",
//    "item.lavafishing.arowana_fish": "Arowana Fish",
//    "item.lavafishing.flame_squat_lobster": "Flame Squat Lobster",
//    "item.lavafishing.obsidian_fishing_rod": "Obsidian Fishing Rod",
//    "item.lavafishing.obsidian_sword_fish": "Obsidian Sword Fish",
//    "item.lavafishing.quartz_fish": "Quartz Fish",
//    "item.lavafishing.scaly_foot_snail": "Scaly Foot Snail",
//    "item.lavafishing.yeti_crab": "Yeti Crab",
//    "item.lavafishing.lava_lamprey": "Lava Lamprey",
//    "item.lavafishing.steam_flying_fish": "Steam Flying Fish",
//    "item.lavafishing.promethium_ingot": "Promethium Ingot",
//    "item.lavafishing.promethium_nugget": "Promethium Nugget",
//    "item.lavafishing.promethium_helmet": "Promethium Helmet",
//    "item.lavafishing.promethium_chestplate": "Promethium Chestplate",
//    "item.lavafishing.promethium_leggings": "Promethium Leggings",
//    "item.lavafishing.promethium_boots": "Promethium Boots",
//    "item.lavafishing.neptunium_slingshot": "Neptunium Slingshot",
//    "item.lavafishing.promethium_slingshot": "Promethium Slingshot",
//    "item.lavafishing.stone_bullet": "Stone Bullet",
//    "item.lavafishing.iron_bullet": "Iron Bullet",
//    "item.lavafishing.neptunium_bullet": "Neptunium Bullet",
//    "item.lavafishing.spicy_fish_fillet": "Spicy Fish Fillet",
//    "item.lavafishing.promethium_bullet": "Promethium Bullet",
//    "block.lavafishing.prometheus_bounty": "Prometheus Bounty",
//    "block.lavafishing.promethium_block": "Promethium Block",
//    "item.minecraft.potion.effect.lava_walker": "Potion of Lava Walker",
//    "item.minecraft.splash_potion.effect.lava_walker": "Splash Potion of Lava Walker",
//    "item.minecraft.lingering_potion.effect.lava_walker": "Lingering Potion of Lava Walker",
//    "sounds.lavafishing.item.slingshot": "Slingshot",
//    "itemGroup.lavafishing": "Lava Fishing",
//    "aquaculture.neptunium_bullet.tooltip.title": "Enemy of the sea",
//    "aquaculture.neptunium_bullet.tooltip.desc": "Track all kinds of aquatic life",
//    "aquaculture.neptunium_slingshot.tooltip.title": "Neptune's Strike",
//    "aquaculture.neptunium_slingshot.tooltip.desc": "Makes bullets go smoothly through water",
//    "lavafishing.promethium_bullet.tooltip.title": "Disaster",
//    "lavafishing.promethium_bullet.tooltip.desc": "Split explosive bullets",
//    "lavafishing.promethium_slingshot.tooltip.title": "Overheating",
//    "lavafishing.promethium_slingshot.tooltip.desc": "Repeating",
//    "lavafishing.double_obsidian_hook.tooltip.title": "Double Barb",
//    "lavafishing.double_obsidian_hook.tooltip.desc": "Chance to catch two things",
//    "lavafishing.glowstone_hook.tooltip.title": "Lucky",
//    "lavafishing.glowstone_hook.tooltip.desc": "Increased luck",
//    "lavafishing.quartz_hook.tooltip.title": "Durable",
//    "lavafishing.quartz_hook.tooltip.desc": "30% chance to not use durability",
//    "lavafishing.soul_sand_hook.tooltip.title": "Enticing",
//    "lavafishing.soul_sand_hook.tooltip.desc": "Increases how long you have to reel in fish",
//    "lavafishing.obsidian_note_hook.tooltip.title": "Alert",
//    "lavafishing.obsidian_note_hook.tooltip.desc": "Plays an alert when a fish is approaching",
//    "lavafishing.promethium_helmet.tooltip.title.1": "Heat Lover",
//    "lavafishing.promethium_helmet.tooltip.title": "Golden Eyes",
//    "lavafishing.promethium_helmet.tooltip.desc.1": "25% fire damage reduction",
//    "lavafishing.promethium_helmet.tooltip.desc": "Watch under the lava clearly",
//    "lavafishing.promethium_chestplate.tooltip.title.1": "Heat Lover",
//    "lavafishing.promethium_chestplate.tooltip.title": "Immolator",
//    "lavafishing.promethium_chestplate.tooltip.desc.1": "25% fire damage reduction",
//    "lavafishing.promethium_chestplate.tooltip.desc": "Slowly regenerates health in the heat",
//    "lavafishing.promethium_leggings.tooltip.title.1": "Heat Lover",
//    "lavafishing.promethium_leggings.tooltip.title": "Ghost",
//    "lavafishing.promethium_leggings.tooltip.desc.1": "25% fire damage reduction",
//    "lavafishing.promethium_leggings.tooltip.desc": "Increases movement speed in the heat",
//    "lavafishing.promethium_boots.tooltip.title.1": "Heat Lover",
//    "lavafishing.promethium_boots.tooltip.title": "Steam",
//    "lavafishing.promethium_boots.tooltip.desc.1": "25% fire damage reduction",
//    "lavafishing.promethium_boots.tooltip.desc": "Walk on the lava"
//}
//
//{
//    "block.lavafishing.prometheus_bounty": "普罗米修斯的恩惠",
//    "block.lavafishing.promethium_block": "钷块",
//    "effect.lavafishing.blessed": "祝福",
//    "effect.lavafishing.endless_flame": "无尽之火",
//    "effect.lavafishing.lava_walker": "岩浆行者",
//    "item.lavafishing.agni_fish": "火神鱼",
//    "item.lavafishing.arowana_fish": "龙鱼",
//    "item.lavafishing.scaly_foot_snail": "鳞足蜗牛",
//    "item.lavafishing.yeti_crab": "雪人蟹",
//    "item.lavafishing.lava_lamprey": "熔岩七鳃鳗",
//    "item.lavafishing.flame_squat_lobster": "烈焰铠甲虾",
//    "item.lavafishing.obsidian_fishing_rod": "黑曜石鱼竿",
//    "item.lavafishing.obsidian_sword_fish": "黑曜石剑鱼",
//    "item.lavafishing.promethium_boots": "钷靴子",
//    "item.lavafishing.promethium_chestplate": "钷胸甲",
//    "item.lavafishing.promethium_helmet": "钷头盔",
//    "item.lavafishing.promethium_ingot": "钷锭",
//    "item.lavafishing.promethium_leggings": "钷护腿",
//    "item.lavafishing.promethium_nugget": "钷粒",
//    "item.lavafishing.neptunium_slingshot": "镎弹弓",
//    "item.lavafishing.promethium_slingshot": "钷弹弓",
//    "item.lavafishing.stone_bullet": "石弹丸",
//    "item.lavafishing.iron_bullet": "铁弹丸",
//    "item.lavafishing.neptunium_bullet": "镎弹丸",
//    "item.lavafishing.promethium_bullet": "钷弹丸",
//    "item.lavafishing.quartz_fish": "石英鱼",
//    "item.lavafishing.steam_flying_fish": "蒸汽飞鱼",
//    "item.lavafishing.spicy_fish_fillet": "麻辣鱼片",
//    "item.minecraft.lingering_potion.effect.lava_walker": "滞留型岩浆行者药水",
//    "item.minecraft.potion.effect.lava_walker": "岩浆行者药水",
//    "item.minecraft.splash_potion.effect.lava_walker": "喷溅型岩浆行者药水",
//    "sounds.lavafishing.item.slingshot": "弹弓",
//    "itemGroup.lavafishing": "岩浆钓鱼",
//    "aquaculture.neptunium_slingshot.tooltip.title": "海王突击",
//    "aquaculture.neptunium_slingshot.tooltip.desc": "使弹丸能顺畅通过水体",
//    "aquaculture.neptunium_bullet.tooltip.title": "海洋公敌",
//    "aquaculture.neptunium_bullet.tooltip.desc": "追踪所有种类的水生生物",
//    "lavafishing.promethium_slingshot.tooltip.title": "过热",
//    "lavafishing.promethium_slingshot.tooltip.desc": "连发",
//    "lavafishing.promethium_bullet.tooltip.title": "祸害",
//    "lavafishing.promethium_bullet.tooltip.desc": "分裂爆炸弹丸",
//    "lavafishing.double_obsidian_hook.tooltip.title": "双钩",
//    "lavafishing.double_obsidian_hook.tooltip.desc": "有几率钓到两个东西",
//    "lavafishing.glowstone_hook.tooltip.title": "幸运",
//    "lavafishing.glowstone_hook.tooltip.desc": "提升运气",
//    "lavafishing.quartz_hook.tooltip.title": "耐用",
//    "lavafishing.quartz_hook.tooltip.desc": "30% 几率不消耗耐久度",
//    "lavafishing.soul_sand_hook.tooltip.title": "诱惑",
//    "lavafishing.soul_sand_hook.tooltip.desc": "延长钓上鱼所需的时间",
//    "lavafishing.obsidian_note_hook.tooltip.title": "警报",
//    "lavafishing.obsidian_note_hook.tooltip.desc": "当鱼接近时发出警报声",
//    "lavafishing.promethium_helmet.tooltip.title.1": "喜热",
//    "lavafishing.promethium_helmet.tooltip.title": "金睛",
//    "lavafishing.promethium_helmet.tooltip.desc.1": "25% 火焰伤害减免",
//    "lavafishing.promethium_helmet.tooltip.desc": "在岩浆下拥有良好视野",
//    "lavafishing.promethium_chestplate.tooltip.title.1": "喜热",
//    "lavafishing.promethium_chestplate.tooltip.title": "焚身",
//    "lavafishing.promethium_chestplate.tooltip.desc.1": "25% 火焰伤害减免",
//    "lavafishing.promethium_chestplate.tooltip.desc": "在炎热时缓慢恢复生命",
//    "lavafishing.promethium_leggings.tooltip.title.1": "喜热",
//    "lavafishing.promethium_leggings.tooltip.title": "幽步",
//    "lavafishing.promethium_leggings.tooltip.desc.1": "25% 火焰伤害减免",
//    "lavafishing.promethium_leggings.tooltip.desc": "提高炎热时的移动速度",
//    "lavafishing.promethium_boots.tooltip.title.1": "喜热",
//    "lavafishing.promethium_boots.tooltip.title": "气浮",
//    "lavafishing.promethium_boots.tooltip.desc.1": "25% 火焰伤害减免",
//    "lavafishing.promethium_boots.tooltip.desc": "在岩浆上行走"
//}