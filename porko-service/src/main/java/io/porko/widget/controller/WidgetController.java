package io.porko.widget.controller;

import static io.porko.widget.controller.WidgetController.WIDGET_BASE_URI;

import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(
    value = WIDGET_BASE_URI,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
@RequiredArgsConstructor
public class WidgetController {
    static final String WIDGET_BASE_URI = "/widget";
    private final WidgetService widgetService;

    @GetMapping
    ResponseEntity<WidgetsResponse> getAllWidgets() {
        return ResponseEntity.ok(widgetService.loadAllWidgets());
    }
}
