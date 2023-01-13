package com.example.munselfservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
