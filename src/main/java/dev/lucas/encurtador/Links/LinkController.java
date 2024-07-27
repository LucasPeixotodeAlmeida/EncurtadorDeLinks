package dev.lucas.encurtador.Links;

import jakarta.servlet.http.HttpServletRequest;
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
        String urlOriginal = request.get("urlOriginal");
        Link link = linkService.encurtarUrl(urlOriginal);

        String gerarUrldeRedirecionamentoDoUsuario = "http://localhost:8090/r/" + link.getUrlNova();

        String createdAtString = link.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LinkResponse linkResponse = new LinkResponse(
                link.getId(),
                link.getUrlOriginal(),
                gerarUrldeRedirecionamentoDoUsuario,
                link.getUrlQrCode(),
                createdAtString
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(linkResponse);
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
