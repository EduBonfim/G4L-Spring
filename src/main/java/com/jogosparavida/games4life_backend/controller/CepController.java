package com.jogosparavida.games4life_backend.controller;



import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate; 
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/cep")
@CrossOrigin(origins = {
    "http://localhost:4200",
    "https://g4-l-angular.vercel.app",
    "https://g4l-spring.onrender.com"
})
public class CepController {
	
	
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/{cep}")
    public ResponseEntity<String> lookupCep(@PathVariable String cep) {
        
        // 1. Api externa sendo consumida para verificaçao de cep
        String urlExterna = "https://viacep.com.br/ws/" + cep + "/json/";
        
        try {
            // 2. O Spring faz o pedido (sem restrições de CORS)
            ResponseEntity<String> response = restTemplate.getForEntity(urlExterna, String.class);
            
            // 3. Devolve a resposta  ao Angular
            return ResponseEntity.status(response.getStatusCode())
                                 .body(response.getBody());
        } catch (Exception e) {
            // Caso a ViaCEP falhe ou o CEP não seja encontrado
            return ResponseEntity.status(500).body("{\"erro\": \"Falha ao consultar CEP no servidor.\" }");
        }
    }

}
