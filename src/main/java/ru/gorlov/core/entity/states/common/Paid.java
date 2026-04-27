package ru.gorlov.core.entity.states.common;

import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.enums.CommonOrderState;

public class Paid extends CommonOrderStateAbstraction {

    @Override
    protected void readyForPickup(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.READY_FOR_PICKUP);
    }
}
