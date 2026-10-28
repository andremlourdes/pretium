package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Pretium - Sistema de Rastreamento de Preços em Supermercados
 * 
 * Esta aplicação permite:
 * - Armazenar dados de compras e cupons de supermercado
 * - Comparar preços de produtos entre supermercados, cidades, estados e países
 * - Visualizar histórico de preços e tendências
 * - Gerar relatórios e gráficos dinâmicos
 */
@SpringBootApplication
@EnableScheduling
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("🛒 Pretium - Sistema de Rastreamento de Preços iniciado!");
    }
}


