package club.redux.sunset.lavafishing.mixin.accessor;

import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Cat.class)
public interface AccessorCat {
    @Accessor("TEMPT_INGREDIENT")
    static void setTemptIngredient(Ingredient ingredient) {
        throw new AssertionError();
    }
}
