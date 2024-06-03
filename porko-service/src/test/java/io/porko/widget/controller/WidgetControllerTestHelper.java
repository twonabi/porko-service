package io.porko.widget.controller;

import io.porko.config.base.WebLayerTestBase;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.domain.Widget;
import io.porko.widget.domain.WidgetCode;
import java.util.List;

public class WidgetControllerTestHelper extends WebLayerTestBase {
    public static List<Widget> widgets;

    static {
        widgets = List.of(
            Widget.of(1L, WidgetCode.TODAY_CONSUMPTION_WEATHER),
            Widget.of(2L, WidgetCode.REMAINING_BUDGET),
            Widget.of(3L, WidgetCode.UPCOMING_EXPENSES),
            Widget.of(4L, WidgetCode.LAST_MONTH_EXPENSES),
            Widget.of(5L, WidgetCode.CURRENT_MONTH_EXPENSES),
            Widget.of(6L, WidgetCode.CURRENT_MONTH_CARD_USAGE),
            Widget.of(7L, WidgetCode.MY_CHALLENGE),
            Widget.of(8L, WidgetCode.DAILY_EXPENSES),
            Widget.of(9L, WidgetCode.CREDIT_SCORE),
            Widget.of(10L, WidgetCode.INVESTMENT_RANKING),
            Widget.of(11L, WidgetCode.STEP_COUNT),
            Widget.of(12L, WidgetCode.DAILY_HOROSCOPE)
        );
    }

    public static WidgetsResponse allWidgets = WidgetsResponse.from(widgets);
}
