package com.labssoft.roteiro01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {
    @GetMapping("/")
    public String index() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                <meta charset="UTF-8">
                <title>To-do List</title>
                <style>
                    html
                    {
                        box-sizing: border-box;
                        overflow: -moz-scrollbars-vertical;
                        overflow-y: scroll;
                    }
                    *,
                    *:before,
                    *:after
                    {
                        box-sizing: inherit;
                    }
                    body
                    {
                        margin:0;
                        background: #fafafa;
                    }
                    a
                    {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        width: 100vw;
                        height: 100vh;
                        color: #007bff;
                        text-decoration: none;
                        background-color: transparent;
                    }
                </style>
                </head>
                <body>
                <a href="/api/docs">Swagger Documentation</a>
                </body>
                </html>
                """;
    }
}
