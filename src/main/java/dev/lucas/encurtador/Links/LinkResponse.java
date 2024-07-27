package dev.lucas.encurtador.Links;

import jakarta.persistence.PrePersist;
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
    //private String idAsString;
    private String urlOriginal;
    private String urlNova;
    private String urlQrCode;
    private String createdAt;

//    @PrePersist
//    public void prePersist() {
//        if (this.id == null) {
//            this.id = UUID.randomUUID();
//        }
//        this.idAsString = this.id.toString(); // Converter UUID para String
//    }
}
