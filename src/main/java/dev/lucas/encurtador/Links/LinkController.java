package dev.lucas.encurtador.Links;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/encurta")
    public ResponseEntity<LinkResponse> gerarUrlEncurtada(@RequestBody Map<String, String> request) {
        try {
            String urlOriginal = request.get("urlOriginal");
            // Chama o serviço para encurtar a URL e gerar o QR code
            Link link = linkService.encurtarUrl(urlOriginal);

            // Gera a URL de redirecionamento para o usuário
            String gerarUrldeRedirecionamentoDoUsuario = "http://localhost:8090/r/" + link.getUrlNova();

            // Cria a resposta com os detalhes do link e do QR code
            LinkResponse linkResponse = new LinkResponse(
                    link.getId(),
                    link.getUrlOriginal(),
                    gerarUrldeRedirecionamentoDoUsuario,
                    link.getUrlQrCode(),
                    link.getCreatedAt().toString()
            );
            // Retorna a resposta com status 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(linkResponse);
        } catch (Exception e) {
            // Em caso de erro, retorna uma resposta com status 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/r/{urlCurta}")
    public void redirecionaLink(@PathVariable String urlCurta, HttpServletResponse response) throws IOException {
        Link link = linkService.obterUrlOriginal(urlCurta);
        if (link != null){
            response.sendRedirect(link.getUrlOriginal());
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
