package model;

import manager.CombatState;

import java.io.Serializable;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public interface Ability extends Serializable{
    void ability(CombatState c);
}
