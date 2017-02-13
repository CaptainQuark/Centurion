package model;

import manager.CombatStateManager;

import java.io.Serializable;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public interface Ability extends Serializable{
    void ability(CombatStateManager c);
}
