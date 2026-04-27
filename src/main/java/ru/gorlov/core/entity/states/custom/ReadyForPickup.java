package ru.gorlov.core.entity.states.custom;

import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.enums.CustomOrderState;

public class ReadyForPickup extends CustomOrderStateAbstraction {

    @Override
    protected void completed(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.COMPLETED);
    }
}
