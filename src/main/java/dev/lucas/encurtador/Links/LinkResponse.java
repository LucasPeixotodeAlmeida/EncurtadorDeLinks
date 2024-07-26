package dev.lucas.encurtador.Links;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkResponse {

    private UUID id;
    private String urlOriginal;
    private String urlNova;
    private String urlQrCode;
}
