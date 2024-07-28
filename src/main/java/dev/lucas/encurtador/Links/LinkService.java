package dev.lucas.encurtador.Links;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LinkService {
    private LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    //IDEIA: UTILIZAR PARTE DO LINK ORIGINAL NA CRIAÇÃO DO NOVO LINK
    public String gerarUrl() {
        return RandomStringUtils.randomAlphanumeric(5, 10);
    }

    public Link encurtarUrl(String urlOriginal) {
        Link link = new Link();
        link.setUrlOriginal(urlOriginal);
        link.setUrlNova(gerarUrl());
        link.setCreatedAt(LocalDateTime.now());
        link.setUrlQrCode("QR INDISPONIVEL");

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
