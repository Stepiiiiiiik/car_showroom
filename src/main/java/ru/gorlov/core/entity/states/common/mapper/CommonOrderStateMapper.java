package ru.gorlov.core.entity.states.common.mapper;

import org.springframework.stereotype.Component;
import ru.gorlov.core.entity.CommonOrderEntity;
import ru.gorlov.core.entity.states.common.Agreed;
import ru.gorlov.core.entity.states.common.AwaitingPayment;
import ru.gorlov.core.entity.states.common.Canceled;
import ru.gorlov.core.entity.states.common.CommonOrderStateAbstraction;
import ru.gorlov.core.entity.states.common.Completed;
import ru.gorlov.core.entity.states.common.Paid;
import ru.gorlov.core.entity.states.common.Placed;
import ru.gorlov.core.entity.states.common.ReadyForPickup;

@Component
public class CommonOrderStateMapper {

    public CommonOrderStateMapper() {
    }

    public CommonOrderStateAbstraction map(CommonOrderEntity order) {
        return switch (order.getState()) {
            case AGREED -> new Agreed();
            case AWAITING_PAYMENT -> new AwaitingPayment();
            case CANCELED -> new Canceled();
            case COMPLETED -> new Completed();
            case PAID -> new Paid();
            case PLACED -> new Placed();
            case READY_FOR_PICKUP -> new ReadyForPickup();
        };
    }
}
