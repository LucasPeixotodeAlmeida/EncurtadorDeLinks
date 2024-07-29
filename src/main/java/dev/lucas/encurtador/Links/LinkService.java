package dev.lucas.encurtador.Links;

import com.google.zxing.WriterException;
import dev.lucas.encurtador.QRCode.QRCodeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class LinkService {
    private LinkRepository linkRepository;

    @Autowired
    private QRCodeService qrCodeService;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    //IDEIA: UTILIZAR PARTE DO LINK ORIGINAL NA CRIAÇÃO DO NOVO LINK
    public String gerarUrl() {
        return RandomStringUtils.randomAlphanumeric(5, 10);
    }

    public Link encurtarUrl(String urlOriginal) throws WriterException, IOException {
        Link link = new Link();
        link.setUrlOriginal(urlOriginal);
        link.setUrlNova(gerarUrl());
        link.setCreatedAt(LocalDateTime.now());

        // Gera a URL curta para redirecionamento
        String urlCurta = "http://localhost:8090/r/" + link.getUrlNova();

        // Gera a imagem do QR code e converte para base64
        byte[] qrCodeImage = qrCodeService.generateQRCodeImage(urlCurta, 200, 200);
        String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);

        // Define a URL do QR code no formato base64
        link.setUrlQrCode("data:image/png;base64," + qrCodeBase64);

        return linkRepository.save(link);
    }

    public Link obterUrlOriginal(String urlNova) {
        try {
            return linkRepository.findByUrlNova(urlNova);
        }catch(Exception erro){
            throw new RuntimeException("URL não encontrado", erro);
        }

    }
}
