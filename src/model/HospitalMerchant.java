package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Entity to handle the healing of {@code Hero}-objects.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class HospitalMerchant {

    private final int maxHeroes = 3;
    private HeroWrapper[] heroes;

    public HospitalMerchant() {
        heroes = new HeroWrapper[maxHeroes];
    }

    public HospitalMerchant(ArrayList<Hero> heroes) {
        this.heroes = new HeroWrapper[maxHeroes];
        ArrayList<Hero> sickHeroes = heroes.stream().filter(Hero::getInMedication).collect(Collectors.toCollection(ArrayList::new));

        if (sickHeroes.size() < maxHeroes)
            heroes.forEach(this::setHeroInNextFreeSlot);

        else{
            heroes.forEach(h -> h.setInMedication(false));
            System.out.println("Not enough medication space available, no hero will be treated.");
        }
    }

    public Integer setHeroInNextFreeSlot(Hero hero) {
        Objects.requireNonNull(hero, "The element to add to the merchant isn't allowed to be null.");

        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] == null) {
                heroes[i] = new HeroWrapper(hero);
                return i;
            }
        }

        System.out.println("No free medication available.");
        return null;
    }

    private HeroWrapper getHeroWrapper(int i) {
        if (i >= 0 && i < maxHeroes)
            return heroes[i];

        throw new IllegalArgumentException("HospitalMerchant : getHeroWrapper() : Provided index is out of bounds.");
    }

    public long getTimeToHealInMillis(int index) {
        return getHeroWrapper(index).timeToHealInMillis;
    }

    public double getCostsToInstantHeal(int index) {
        return getHeroWrapper(index).calculateCostsToInstantHeal();
    }

    public boolean instantantlyHealHero(int index) {
        if (index < 0 && index >= maxHeroes)
            throw new IllegalArgumentException("HospitalMerchant : instantHealHeroByIndex() : Provided index is out of bounds.");

        heroes[index].getHero().setHp(heroes[index].getHero().getHpTotal());
        heroes[index].getHero().setInMedication(false);
        heroes[index] = null;
        return true;
    }

    public boolean abortMedication(int index) {
        if (index < 0 && index >= maxHeroes)
            throw new IllegalArgumentException("HospitalMerchant : abortMedication() : Provided index is out of bounds.");

        heroes[index].getHero().setInMedication(false);
        heroes[index] = null;
        return true;
    }

    private class HeroWrapper {

        final Hero hero;
        final long timeToHealInMillis;

        HeroWrapper(Hero hero) {
            this.hero = hero;
            timeToHealInMillis = calculateTimeToHealInMillis();
        }

        Hero getHero() {
            return hero;
        }

        long calculateTimeToHealInMillis() {

            // One lost HP takes 5 minutes to heal.
            return hero.getHp() * 5 * 60 * 1000;
        }

        double calculateCostsToInstantHeal() {

            // Casting to double tested, works.
            return (hero.getHpTotal() - hero.getHp()) * (hero.getPurchaseCosts() / 1000.0);
        }
    }
}
