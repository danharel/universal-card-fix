package UniversalCardFix.patches.powers;

import UniversalCardFix.patches.relics.PaperCranePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class WeakPowerPatch {
    @SpirePatch(clz = WeakPower.class, method = "atDamageGive")
    public static class AtDamageGive {
        public static float Replace(WeakPower __instance, float damage, DamageInfo.DamageType type) {
            if (type == DamageInfo.DamageType.NORMAL) {
                if (!__instance.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane")) {
                    return damage * (1.0f - PaperCranePatch.WEAK_PERCENTAGE);
                }
                return damage * 0.75f;
            }
            return damage;
        }
    }

    @SpirePatch(clz = WeakPower.class, method = "updateDescription")
    public static class UpdateDescription {
        public static void Replace(WeakPower __instance) {
            bool usePaperCrane = __instance.owner != null && !__instance.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane");
            int weakPercentage = usePaperCrane ? PaperCranePatch.WEAK_PERCENTAGE : 25;
            int damageReduction = Math.round(100 * weakPercentage);
            String suffix = __instance.amount == 1 ? __instance.DESCRIPTIONS[2] : __instance.DESCRIPTIONS[3];
            __instance.description = String.format("%s%d%s%d%s", __instance.DESCRIPTIONS[0], damageReduction, __instance.DESCRIPTIONS[1], __instance.amount, suffix)
        }
    }
}
