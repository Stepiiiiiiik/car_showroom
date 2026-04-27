package ru.gorlov.core.entity.states.custom;

import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.enums.CustomOrderState;

public class AwaitingDelivery extends CustomOrderStateAbstraction {

    @Override
    protected void readyForPickup(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.READY_FOR_PICKUP);
    }
}
