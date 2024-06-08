package com.labssoft.roteiro01.unit.controller;

import com.labssoft.roteiro01.controller.SwaggerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwaggerControllerTest {

    private SwaggerController sut;

    @BeforeEach
    void setUp() {
        sut = new SwaggerController();
    }

    @Test
    @DisplayName("Should return Swagger documentation HTML")
    void should_return_swagger_documentation_html() {
        // Act
        String result = sut.index();

        // Assert
        String expectedHtml = """
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
        assertEquals(expectedHtml, result);
    }
}