package org.JakovSlafhauzer.praksa.zad2;

public record UpdateTodoRequest(
        String title,
        Boolean completed
) {
}
