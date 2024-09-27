package br.com.avancard.jpa_hibernate;

import javax.persistence.EntityManager; // Importa a classe EntityManager, usada para interagir com o contexto de persistência.
import javax.persistence.EntityManagerFactory; // Importa a classe EntityManagerFactory, que cria EntityManager.
import javax.persistence.Persistence; // Importa a classe Persistence, usada para criar uma instância de EntityManagerFactory.

public class HibernateUtil { // Declara a classe HibernateUtil.

    // Declara uma variável estática de classe para armazenar a fábrica de EntityManagers.
    public static EntityManagerFactory factory = null;

    // Bloco estático que é executado quando a classe é carregada. Chama o método init() para inicializar a fábrica.
    static {
        init();
    }

    // Método privado estático para inicializar a fábrica de EntityManager.
    private static void init() {
        try {
            // Verifica se a fábrica ainda não foi inicializada.
            if (factory == null) {
                // Cria a fábrica de EntityManager usando o nome da unidade de persistência definida em persistence.xml.
                factory = Persistence.createEntityManagerFactory("jpa_hibernate");
            }
        } catch (Exception e) {
            // Captura qualquer exceção que ocorra durante a inicialização e imprime a stack trace.
            e.printStackTrace();
        }
    }

    // Método público estático que retorna uma nova instância de EntityManager.
    public static EntityManager getEntityManager() {
        // Cria e retorna um novo EntityManager a partir da fábrica.
        return factory.createEntityManager();
    }
}
