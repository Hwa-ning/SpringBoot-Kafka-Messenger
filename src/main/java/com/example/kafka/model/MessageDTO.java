package com.example.kafka.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO implements Serializable {
    private static final long serialVersionUID = 213456789;

    @Builder.Default
    private String author = "Unknown";
    private String content;
    @Builder.Default
    private LocalDateTime sendingTime = LocalDateTime.now();
}