package ru.gorlov.core.entity.states.common;

import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.enums.CommonOrderState;

public class ReadyForPickup extends CommonOrderStateAbstraction {

    @Override
    protected void completed(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.COMPLETED);
    }
}
