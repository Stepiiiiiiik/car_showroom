package ru.gorlov.core.entity.states.common;

import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.enums.CommonOrderState;

public class AwaitingPayment extends CommonOrderStateAbstraction {

    @Override
    protected void paid(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.PAID);
    }

    protected void canceled(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.CANCELED);
    }
}
