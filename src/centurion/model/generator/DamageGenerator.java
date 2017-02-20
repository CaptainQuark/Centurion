package centurion.model.generator;

import centurion.model.model.Hero;
import centurion.model.model.Monster;

import java.util.Random;

/**
 * Calculates damage created by various creatures.
 * @author Thomas Schönmann
 * @version %I%
 */
public class DamageGenerator {

    private DamageGenerator() {}

    public static int calculateMonsterDamage(Monster monster, Hero hero, int bonusEvasion){
        Random random = new Random();

        //Trifft das Monster?
        if (random.nextInt(1000) < monster.getAccuracy()) {

            //Kann der Held ausweichen?
            if (random.nextInt(1000) < hero.getEvasion() - bonusEvasion) {
                int monsterDmg = random.nextInt(monster.getMaxDmg() - monster.getMinDmg()) + monster.getMinDmg();

                //Krittet das Monster?
                if ((Math.random() * 1000) > monster.getCritChance()) {
                     return monsterDmg *= monster.getCritMultiplier();
                }
            }
        }

        return -1;
    }
}
