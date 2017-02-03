package model;

import manager.CombatManager;

import java.util.Observable;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public interface Ability {
    void ability(CombatManager c);
}
