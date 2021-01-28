package com.example.desafiogabriela

import androidx.test.platform.app.InstrumentationRegistry

fun String.loadAsFixture(): String {
    val context = InstrumentationRegistry.getInstrumentation().context
    return context.assets
        .open("fixtures/$this")
        .bufferedReader()
        .readText()
}

// Carregar primeira pagina da lista de repositórios
// 1. Mockar resposta da API para retornar pagina 1 da lista de repositórios
// 2. Abrir activity com lista de repositórios
// 2.1 Colocar uma "espera" até a lista ser carregada
// 2.2 Tentar várias vezes até a resposta da api chegar
// 2.3 Usar idling resource
// 3. Verifico se o item CS-Notes está na lista