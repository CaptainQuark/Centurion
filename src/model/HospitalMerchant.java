package model;

import dao.DAO;
import enumerations.FileNames;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<HeroWrapper> hospitalHeroes;
    private ArrayList<Hero> userHeroes;
    private DAO dao;

    public HospitalMerchant(DAO dao) {
        this.dao = dao;

        this.hospitalHeroes = new ArrayList<>(maxHeroes);
        this.hospitalHeroes.addAll(Arrays.asList(null, null, null));
        this.userHeroes = getFromDisk();

        System.out.println("userHeroes: " + userHeroes.size());

        if (userHeroes.stream().filter(Hero::getInMedication).collect(Collectors.toCollection(ArrayList::new)).size() < maxHeroes)
            userHeroes.stream().filter(Hero::getInMedication).collect(Collectors.toCollection(ArrayList::new)).forEach(this::setHeroInNextFreeSlot);

        else {
            userHeroes.forEach(h -> h.setInMedication(false));
            dao.saveList(FileNames.USER_HEROES.name(), hospitalHeroes);
            System.out.println("Not enough medication space available, no hero will be treated and everyone is freed from medication. Critical error.");
        }
    }


    public Integer setHeroInNextFreeSlot(Hero hero) {
        Objects.requireNonNull(hero, "The element to add to the merchant isn't allowed to be null.");

        System.out.println("User heroes: " + userHeroes.size());

        // Alter both the local 'hero' (saved in Wrapper) + matching Hero-Object in user's list. Order is important!
        for (int i = 0; i < maxHeroes; i++)
            if (hero.getElementID() == userHeroes.get(i).getElementID())
                userHeroes.get(i).setInMedication(true);

        hero.setInMedication(true);

        for (int i = 0; i < maxHeroes; i++)
            if (hospitalHeroes.get(i) == null) {
                System.out.println("Adding to HeroWrapper: " + (hospitalHeroes.set(i, new HeroWrapper(hero)) != null));
                System.out.println("Saving all users: " + saveUserHeroesToDisk());
                return i;
            }

        System.out.println("No free medication available.");
        return null;
    }

    private HeroWrapper getHeroWrapper(int i) {
        if (i >= 0 && i < maxHeroes)
            return hospitalHeroes.get(i);

        throw new IllegalArgumentException("HospitalMerchant : getHeroWrapper() : Provided index is out of bounds.");
    }

    public ArrayList<Hero> getHeroesInMedication() {
        return hospitalHeroes.stream().filter(Objects::nonNull).map(HeroWrapper::getHero).collect(Collectors.toCollection(ArrayList::new));
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

        int indexToOverride = userHeroes.indexOf(hospitalHeroes.get(index).getHero());
        userHeroes.get(indexToOverride).setInMedication(false);
        hospitalHeroes.set(index, null);

        return saveUserHeroesToDisk();
    }

    public boolean abortMedication(int index) {
        if (index < 0 && index >= maxHeroes)
            throw new IllegalArgumentException("HospitalMerchant : abortMedication() : Provided index is out of bounds.");

        int indexToOverride = userHeroes.indexOf(hospitalHeroes.get(index).getHero());
        userHeroes.get(indexToOverride).setInMedication(false);
        return hospitalHeroes.set(index, null) == null && saveUserHeroesToDisk();
    }

    private boolean saveUserHeroesToDisk() {
        if (dao == null || userHeroes == null)
            throw new NullPointerException("AbstractMerchant : saveUserHeroesToDisk : DAO or items aren't allowed to be null.");

        return dao.saveList(FileNames.USER_HEROES.name(), userHeroes);
    }

    private ArrayList<Hero> getFromDisk() {
        if (dao == null || hospitalHeroes == null)
            throw new NullPointerException("AbstractMerchant : getFromDisk : DAO or hospitalHeroes aren't allowed to be null.");

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
            return (hero.getHpTotal() - hero.getHp()) * 5 * 60 * 1000;
        }

        double calculateCostsToInstantHeal() {

            // Casting to double tested, works.
            return (hero.getHpTotal() - hero.getHp()) * (hero.getPurchaseCosts() / 1000.0);
        }
    }
}
