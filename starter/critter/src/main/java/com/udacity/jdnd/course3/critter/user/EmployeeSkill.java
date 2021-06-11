package com.udacity.jdnd.course3.critter.user;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

/**
 * A example list of employee skills that could be included on an employee or a schedule request.
 */
//@Embeddable
public enum EmployeeSkill {
    PETTING, WALKING, FEEDING, MEDICATING, SHAVING;

    EmployeeSkill() {
    }
}
