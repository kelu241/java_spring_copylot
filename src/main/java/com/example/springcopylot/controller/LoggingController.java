package com.example.springcopylot.controller;

import com.example.springcopylot.logging.LogExecution;
import com.example.springcopylot.logging.LoggingExampleService;
import com.example.springcopylot.logging.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller para demonstrar e testar o sistema de logging personalizado
 */
@RestController
@RequestMapping("/logging")
public class LoggingController {
    
    @Autowired
    private LoggingExampleService loggingExampleService;
    
    /**
     * Endpoint para testar logging manual
     */
    @GetMapping("/manual")
    @LogExecution(includeResult = true)
    public ResponseEntity<Map<String, String>> testeLoggingManual() {
        CustomLogger.logInfo("Testando logging manual via endpoint");
        
        loggingExampleService.exemploLoggingManual();
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Teste de logging manual executado");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para testar logging automático
     */
    @GetMapping("/automatico")
    @LogExecution(includeParameters = true, includeResult = true)
    public ResponseEntity<String> testeLoggingAutomatico(@RequestParam String nome, @RequestParam int idade) {
        String resultado = loggingExampleService.exemploLoggingCompleto(nome, idade);
        return ResponseEntity.ok(resultado);
    }
    
    /**
     * Endpoint para testar logging de erro
     */
    @GetMapping("/erro")
    public ResponseEntity<Map<String, String>> testeLoggingErro(@RequestParam(defaultValue = "false") boolean gerarErro) {
        try {
            loggingExampleService.exemploComExcecao(gerarErro);
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Nenhum erro gerado");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            CustomLogger.logError("Erro capturado no controller", e);
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Endpoint para testar logging com contexto
     */
    @PostMapping("/contexto")
    @LogExecution(includeParameters = true)
    public ResponseEntity<Map<String, String>> testeLoggingContexto(
            @RequestParam String userId, 
            @RequestParam String operation) {
        
        loggingExampleService.exemploLoggingComContexto(userId, operation);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Teste de logging com contexto executado");
        response.put("userId", userId);
        response.put("operation", operation);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para testar diferentes níveis de log
     */
    @GetMapping("/niveis")
    public ResponseEntity<Map<String, String>> testeNiveisLog() {
        CustomLogger.logDebug("Log de debug - detalhes técnicos");
        CustomLogger.logInfo("Log de info - informação geral");
        CustomLogger.logWarn("Log de warning - atenção necessária");
        
        try {
            throw new RuntimeException("Erro de exemplo");
        } catch (Exception e) {
            CustomLogger.logError("Log de erro - falha na operação", e);
        }
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Logs de diferentes níveis foram gerados");
        
        return ResponseEntity.ok(response);
    }
}