package br.com.luizgasc.screenmatch.service;


import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;


public class ConsultaChatGPT {


    public static String obterTraducao(String texto) {
        String APIKEY = "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";
        OpenAiService service = new OpenAiService(APIKEY);

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}
