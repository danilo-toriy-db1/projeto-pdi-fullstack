package com.project.my_landing_page.mockData;

public class SobreMimPaginaMock {
    public static String getJsonCompleto() {
        return """
                  {
                    "sobreMim": {
                        "nome": "Usuário",
                        "idade": 10,
                        "carreira": "Tester",
                        "profissao": "Testes",
                        "empresa": "Test"
                        },
                    "biografia": "Bio nova",
                    "hobbie": "Hobbie",
                    "objetivo": "Objetivo",
                    "desgosto": "Desgosto",
                    "habilidades": null
                    }
                  """;
    }
}
