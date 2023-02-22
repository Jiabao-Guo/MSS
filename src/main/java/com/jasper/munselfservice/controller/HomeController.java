package com.jasper.munselfservice.controller;

import com.jasper.munselfservice.controller.forms.task.TaskCreateForm;
import com.jasper.munselfservice.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RestController
public class HomeController extends BaseController {
    @RequestMapping("/")
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>MUN Self Service</title>
            </head>
            <body>
                <h1>MUN Self Service</h1>
                <p>Welcome to MUN Self Service!</p>
                <p>Current Time: %s</p>
            </body>
            </html>
            """.formatted(java.time.LocalDateTime.now());
    }
}
