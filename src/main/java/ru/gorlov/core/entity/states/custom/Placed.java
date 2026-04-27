package ru.gorlov.core.entity.states.custom;

import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.enums.CustomOrderState;

public class Placed extends CustomOrderStateAbstraction {

    @Override
    protected void agreed(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.AGREED);
    }

    @Override
    protected void canceled(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.CANCELED);
    }
}
