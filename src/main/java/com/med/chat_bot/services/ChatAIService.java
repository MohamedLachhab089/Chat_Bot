package com.med.chat_bot.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@BrowserCallable // c'est comme on dit je voudrais tout ces methodes qui se trouve dans cette classe je voulais les appeles directement via mon frontend
@AnonymousAllowed // j'ai pas besoin de passer via l'authentification pour faire appeler cette fct la
public class ChatAIService {

    private ChatClient chatClient;
    private VectorStore vectorStore;
    @Value("classpath:/prompts/prompt-template.st")
    private Resource promptResource;

    public ChatAIService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    public String RagChat(String question) {
        // Recherche de documents similaires
        List<Document> documents = vectorStore.similaritySearch(question);
        List<String> context = documents.stream().map(Document::getContent).toList();

        // Création d'un prompt à partir du template
        PromptTemplate promptTemplate = new PromptTemplate(promptResource);
        Prompt prompt = promptTemplate.create(Map.of("context", context, "question", question));

        // Appel au client pour obtenir la réponse
        return chatClient.prompt(prompt)
                //.user(question)
                .call()
                .content();
    }

}
