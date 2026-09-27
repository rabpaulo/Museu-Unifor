# 🏛️ Museu Unifor - Aplicativo Mobile

Aplicativo móvel desenvolvido em **Android nativo com Jetpack Compose** para o **Espaço Cultural da Universidade de Fortaleza (Unifor)**, com foco na exposição **"Centelhas em Movimento"** (Coleção Igor Queiroz Barroso).

O projeto visa transformar a visita ao museu em uma experiência imersiva, acessível e interativa, unindo tecnologia de ponta, acessibilidade e arte brasileira moderna.

---

## ✨ Destaque: Assistente Virtual com a API do Google Gemini

Uma das principais funcionalidades do aplicativo é a integração com a **API do Google Gemini**, que transforma o visitante em um participante ativo através de um chat interativo com Inteligência Artificial generativa diretamente na tela de detalhes das obras ([`ObraGenericaScreen`](file:///home/paulo/Projects/Museu-Unifor/app/src/main/java/com/example/mobile/ui/screens/visitor/ObraGenericaScreen.kt)).

### 🤖 Como Funciona o Chat Cultural

- **Modelo Utilizado:** `gemini-1.5-flash` via SDK oficial [Google Generative AI Client](https://github.com/google/generative-ai-android) (`com.google.ai.client.generativeai:generativeai:0.9.0`).
- **Persona Contextualizada:** O assistente atua como um Guia Cultural especializado na exposição *"Centelhas em Movimento"*, fornecendo respostas concisas, didáticas e culturalmente ricas sobre:
  - Contexto histórico e artístico da obra em exibição;
  - Vida, importância e movimentos ligados aos artistas (ex.: Cândido Portinari, Tarsila do Amaral, entre outros);
  - Técnicas artísticas empregadas;
  - Curiosidades do acervo e da coleção do Espaço Cultural Unifor.
- **Sugestões Rápidas (Quick Prompts):** Para facilitar a interação, o chat disponibiliza chips com perguntas frequentes:
  - *"Quem é este artista?"*
  - *"Qual o contexto da obra?"*
  - *"Que técnica foi usada?"*
  - *"Fale sobre a exposição"*
- **Feedback em Tempo Real:** Indicador visual de digitação (*"Gemini está digitando..."*) enquanto a resposta é gerada assincronamente.
- **Resiliência e Fallback:** Em caso de perda momentânea de conexão ou restrição de rede, o repositório conta com respostas inteligentes de fallback sobre os grandes mestres e o museu.

### 🧩 Arquitetura do Módulo Gemini

A funcionalidade segue o padrão **MVVM (Model-View-ViewModel)** e o **Repository Pattern**:

```
[UI: ObraGenericaScreen / ChatScreen] 
        ▲
        │  (StateFlow: messages, isLoading)
        ▼
[ViewModel: GeminiViewModel]
        ▲
        │  (Coroutines & Result<String>)
        ▼
[Repository: GeminiRepository]
        ▲
        │  (GenerativeModel - gemini-1.5-flash)
        ▼
[Google Gemini API]
```

- **[`GeminiRepository.kt`](file:///home/paulo/Projects/Museu-Unifor/app/src/main/java/com/example/mobile/data/repository/GeminiRepository.kt):** Encapsula as chamadas à API do Gemini, configuração da chave de acesso e montagem do prompt do assistente cultural.
- **[`GeminiViewModel.kt`](file:///home/paulo/Projects/Museu-Unifor/app/src/main/java/com/example/mobile/ui/viewmodel/GeminiViewModel.kt):** Gerencia o histórico de mensagens (`ChatMessage`), estado de carregamento e dispara as requisições em background via `viewModelScope`.
- **[`ChatScreen.kt`](file:///home/paulo/Projects/Museu-Unifor/app/src/main/java/com/example/mobile/ui/components/ChatScreen.kt):** Interface moderna construída em Jetpack Compose, com balões de conversa distintos para usuário e IA, chips de sugestões e rolagem automática para a mensagem mais recente.

---

## 🎨 Funcionalidades do Aplicativo

### 👤 Módulo Visitante
- **Exploração de Obras e Artistas:** Catálogo completo das obras e biografia dos artistas presentes na exposição.
- **Detalhes da Obra:** Visualização de imagens em alta qualidade, ficha técnica, descrição e integração com o assistente virtual.
- **Acessibilidade Completa:**
  - 🔊 **Audiodescrição (TTS):** Narração de voz nativa através do `TextToSpeechHelper`.
  - 🤟 **VLibras:** Tradução automática do conteúdo textual para a Língua Brasileira de Sinais em avatar 3D (via WebView).
- **Chat com o Gemini:** Diálogo inteligente e interativo sobre qualquer dúvida artística da exposição.

### 🔐 Módulo Administrativo (ADM)
- **Autenticação Segura:** Login administrativo integrado ao **Firebase Authentication**.
- **Gestão de Acervo (CRUD):** Cadastro, edição e remoção de obras e autores sincronizados com o **Cloud Firestore**.
- **Upload de Imagens:** Seleção e inclusão de fotos das obras e retratos dos artistas.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **Interface Declarativa:** Jetpack Compose + Material 3
- **Inteligência Artificial:** [Google Generative AI SDK](https://ai.google.dev/) (`gemini-1.5-flash`)
- **Backend & Cloud:** Firebase Authentication & Cloud Firestore
- **Carregamento de Imagens:** Coil Compose
- **Acessibilidade:** Android TextToSpeech & Integração VLibras Widget
- **Navegação:** Jetpack Navigation Compose
- **Arquitetura:** MVVM + Clean Architecture / Repository Pattern

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
1. **Android Studio** (Hedgehog ou superior recomendado).
2. **JDK 17** ou superior configurado.
3. Dispositivo físico ou Emulador com **Android 7.0+ (API 24+)**.

### Passos para Configuração

1. **Clonar o Repositório:**
   ```bash
   git clone https://github.com/rabpaulo/Museu-Unifor.git
   cd Museu-Unifor
   ```

2. **Configuração da API do Gemini:**
   - Obtenha uma chave de API gratuita no [Google AI Studio](https://aistudio.google.com/).
   - Configure a chave no arquivo [`GeminiRepository.kt`](file:///home/paulo/Projects/Museu-Unifor/app/src/main/java/com/example/mobile/data/repository/GeminiRepository.kt) ou via variável de ambiente/BuildConfig conforme suas preferências de segurança.

3. **Configuração do Firebase:**
   - Certifique-se de que o arquivo `google-services.json` está presente na pasta `app/`.

4. **Compilar e Executar:**
   - Abra o projeto no Android Studio.
   - Aguarde o sync do Gradle.
   - Execute no dispositivo de sua preferência (`Shift + F10` ou botão *Run*).

---

## 👥 Equipe e Instituição

Projeto desenvolvido para o **Espaço Cultural Unifor**, promovendo a cultura, arte e educação da **Universidade de Fortaleza (UNIFOR)**.
