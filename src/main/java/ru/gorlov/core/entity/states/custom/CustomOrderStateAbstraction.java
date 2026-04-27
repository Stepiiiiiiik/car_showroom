package ru.gorlov.core.entity.states.custom;

import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.enums.CustomOrderState;

public abstract class CustomOrderStateAbstraction {

    public void transitTo(CustomOrderEntity order,
        CustomOrderState state) {
        switch (state) {
            case AGREED:
                agreed(order);
            case AWAITING_PAYMENT:
                awaitingPayment(order);
            case AWAITING_DELIVERY:
                awaitingDelivery(order);
            case CANCELED:
                canceled(order);
            case COMPLETED:
                completed(order);
            case PAID:
                paid(order);
            case PLACED:
                placed(order);
            case READY_FOR_PICKUP:
                readyForPickup(order);
        }
    }

    protected void agreed(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void awaitingPayment(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void canceled(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void completed(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void paid(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void placed(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void awaitingDelivery(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void readyForPickup(CustomOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }
}
