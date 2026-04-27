package ru.gorlov.core.entity.states.custom;

import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.enums.CustomOrderState;

public class AwaitingPayment extends CustomOrderStateAbstraction {

    @Override
    protected void paid(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.PAID);
    }

    @Override
    protected void canceled(CustomOrderEntity customOrder) {
        customOrder.setState(CustomOrderState.CANCELED);
    }
}
