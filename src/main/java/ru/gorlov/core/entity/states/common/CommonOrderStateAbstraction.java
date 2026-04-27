package ru.gorlov.core.entity.states.common;

import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.enums.CommonOrderState;

public abstract class CommonOrderStateAbstraction {

    public void transitTo(CommonOrderEntity order,
        CommonOrderState state) {
        switch (state) {
            case AGREED:
                agreed(order);
                break;
            case AWAITING_PAYMENT:
                awaitingPayment(order);
                break;
            case CANCELED:
                canceled(order);
                break;
            case COMPLETED:
                completed(order);
                break;
            case PAID:
                paid(order);
                break;
            case PLACED:
                placed(order);
                break;
            case READY_FOR_PICKUP:
                readyForPickup(order);
                break;
        }
    }

    protected void agreed(CommonOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void awaitingPayment(CommonOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void canceled(CommonOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void completed(CommonOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void paid(CommonOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void placed(CommonOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }

    protected void readyForPickup(CommonOrderEntity commonOrder) {
        throw new IllegalStateException("Transition is not allowed");
    }
}
