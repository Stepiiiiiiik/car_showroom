package ru.gorlov.core.entity.states.custom;

import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.enums.CustomOrderState;

public class Paid extends CustomOrderStateAbstraction {

    @Override
    protected void awaitingDelivery(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.AWAITING_DELIVERY);
    }
}
