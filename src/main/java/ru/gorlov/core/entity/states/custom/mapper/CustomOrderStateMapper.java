package ru.gorlov.core.entity.states.custom.mapper;

import org.springframework.stereotype.Component;
import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.core.entity.states.custom.Agreed;
import ru.gorlov.core.entity.states.custom.AwaitingDelivery;
import ru.gorlov.core.entity.states.custom.AwaitingPayment;
import ru.gorlov.core.entity.states.custom.Canceled;
import ru.gorlov.core.entity.states.custom.Completed;
import ru.gorlov.core.entity.states.custom.CustomOrderStateAbstraction;
import ru.gorlov.core.entity.states.custom.Paid;
import ru.gorlov.core.entity.states.custom.Placed;
import ru.gorlov.core.entity.states.custom.ReadyForPickup;

@Component
public class CustomOrderStateMapper {

    public CustomOrderStateMapper() {
    }

    public CustomOrderStateAbstraction map(CustomOrderEntity order) {
        return switch (order.getState()) {
            case AGREED -> new Agreed();
            case AWAITING_DELIVERY -> new AwaitingDelivery();
            case AWAITING_PAYMENT -> new AwaitingPayment();
            case CANCELED -> new Canceled();
            case COMPLETED -> new Completed();
            case PAID -> new Paid();
            case PLACED -> new Placed();
            case READY_FOR_PICKUP -> new ReadyForPickup();
        };
    }
}
