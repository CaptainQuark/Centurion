package model;

import manager.CombatManager;

import java.io.Serializable;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public interface Ability extends Serializable{
    void ability(CombatManager c);
}
