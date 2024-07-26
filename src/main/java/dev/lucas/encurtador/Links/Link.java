package dev.lucas.encurtador.Links;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String urlOriginal;
    private String urlNova;
    private String urlQrCode;

    @Timestamp
    private LocalDateTime createdAt;
}
