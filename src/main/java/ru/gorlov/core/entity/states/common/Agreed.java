package ru.gorlov.core.entity.states.common;

import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.enums.CommonOrderState;

public class Agreed extends CommonOrderStateAbstraction {

    @Override
    protected void awaitingPayment(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.AWAITING_PAYMENT);
    }

    @Override
    protected void canceled(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.CANCELED);
    }
}
