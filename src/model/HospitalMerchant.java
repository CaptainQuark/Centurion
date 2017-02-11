package model;

import dao.DAO;
import enumerations.FileNames;

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
    private ArrayList<HeroWrapper> heroes;
    private ArrayList<Hero> userHeroes;
    private DAO dao;

    public HospitalMerchant(DAO dao) {
        this.dao = dao;

        this.heroes = new ArrayList<>();
        this.userHeroes = getFromDisk().stream().filter(Hero::getInMedication).collect(Collectors.toCollection(ArrayList::new));

        if (userHeroes.size() < maxHeroes)
            userHeroes.forEach(this::setHeroInNextFreeSlot);

        else {
            userHeroes.forEach(h -> h.setInMedication(false));
            dao.saveList(FileNames.USER_HEROES.name(), heroes);
            System.out.println("Not enough medication space available, no hero will be treated and everyone is freed from medication. Critical error.");
        }
    }


    public Integer setHeroInNextFreeSlot(Hero hero) {
        Objects.requireNonNull(hero, "The element to add to the merchant isn't allowed to be null.");
        hero.setInMedication(true);

        for (int i = 0; i < maxHeroes; i++)
            if (heroes.get(i) == null)
                return heroes.set(i, new HeroWrapper(hero)) != null && saveUserHeroesToDisk() ? i : null;

        System.out.println("No free medication available.");
        return null;
    }

    private HeroWrapper getHeroWrapper(int i) {
        if (i >= 0 && i < maxHeroes)
            return heroes.get(i);

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

        int indexToOverride = userHeroes.indexOf(heroes.get(index).getHero());
        userHeroes.get(indexToOverride).setInMedication(false);
        heroes.set(index, null);

        return saveUserHeroesToDisk();
    }

    public boolean abortMedication(int index) {
        if (index < 0 && index >= maxHeroes)
            throw new IllegalArgumentException("HospitalMerchant : abortMedication() : Provided index is out of bounds.");

        int indexToOverride = userHeroes.indexOf(heroes.get(index).getHero());
        userHeroes.get(indexToOverride).setInMedication(false);
        return heroes.set(index, null) == null && saveUserHeroesToDisk();
    }

    private boolean saveUserHeroesToDisk() {
        if (dao == null || userHeroes == null)
            throw new NullPointerException("AbstractMerchant : saveUserHeroesToDisk : DAO or items aren't allowed to be null.");

        return dao.saveList(FileNames.USER_HEROES.name(), userHeroes);
    }

    private ArrayList<Hero> getFromDisk() {
        if (dao == null || heroes == null)
            throw new NullPointerException("AbstractMerchant : getFromDisk : DAO isn't allowed to be null.");

        return dao.getAllElements(FileNames.USER_HEROES.name());
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
