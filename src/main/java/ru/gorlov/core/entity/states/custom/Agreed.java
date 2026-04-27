package ru.gorlov.core.entity.states.custom;


import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.enums.CustomOrderState;

public class Agreed extends CustomOrderStateAbstraction {

    @Override
    protected void awaitingPayment(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.AWAITING_PAYMENT);
    }

    @Override
    protected void canceled(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.CANCELED);
    }
}
