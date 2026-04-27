package ru.gorlov.core.entity.states.common;

import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.enums.CommonOrderState;

public class Placed extends CommonOrderStateAbstraction {

    @Override
    protected void agreed(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.AGREED);
    }

    @Override
    protected void canceled(CommonOrderEntity commonOrder) {
        commonOrder.setState(CommonOrderState.CANCELED);
    }
}
