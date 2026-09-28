document.addEventListener('DOMContentLoaded', () => {

    // 1. Smooth Scrolling for Anchor Links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // 2. Gemini AI Chat Simulator
    const chatBody = document.getElementById('chat-body');
    const chips = document.querySelectorAll('.chip');
    
    // Respostas simuladas do Gemini com base nos prompts
    const geminiResponses = {
        "Quem é Tarsila do Amaral?": "Tarsila do Amaral (1886–1973) foi uma pintora e desenhista central do modernismo brasileiro. Ela fez parte do 'Grupo dos Cinco' e foi a criadora do quadro 'Abaporu', que inaugurou o movimento Antropofágico no Brasil. Na nossa exposição, você pode ver suas obras 'Abaporu' e 'A Cuca'.",
        "Fale sobre a obra Retirantes": "A obra 'Retirantes' (1944) de Cândido Portinari é uma pintura expressionista profunda. Ela retrata a dolorosa migração de famílias nordestinas fugindo da seca. Portinari utiliza tons terrosos e figuras esqueléticas para denunciar a miséria social de forma dramática.",
        "O que é o Movimento Antropofágico?": "O Movimento Antropofágico foi uma vanguarda cultural brasileira dos anos 1920. Inspirado pela obra 'Abaporu' de Tarsila, propunha 'devorar' a cultura estrangeira europeia e misturá-la com as raízes indígenas e africanas do Brasil, criando uma arte genuinamente nacional.",
        "Me explique a obra Abaporu": "'Abaporu' significa 'o homem que come gente' em tupi-guarani. Pintada em 1928, a obra mostra uma figura de pés e mãos gigantescos (ligação com o trabalho na terra) e cabeça pequena (crítica ao trabalho braçal sem intelectualidade), sob um sol inclemente e um cacto."
    };

    function appendMessage(text, isUser = false) {
        const msgDiv = document.createElement('div');
        msgDiv.className = `message ${isUser ? 'user-message' : 'ai-message'}`;
        msgDiv.textContent = text;
        chatBody.appendChild(msgDiv);
        chatBody.scrollTop = chatBody.scrollHeight;
    }

    function showTypingIndicator() {
        const typingDiv = document.createElement('div');
        typingDiv.className = 'typing-indicator';
        typingDiv.id = 'typing-indicator';
        typingDiv.innerHTML = '<i class="fas fa-circle-notch fa-spin"></i> O Gemini está formulando a resposta...';
        chatBody.appendChild(typingDiv);
        chatBody.scrollTop = chatBody.scrollHeight;
    }

    function removeTypingIndicator() {
        const indicator = document.getElementById('typing-indicator');
        if (indicator) {
            indicator.remove();
        }
    }

    chips.forEach(chip => {
        chip.addEventListener('click', () => {
            const prompt = chip.getAttribute('data-prompt');
            
            // 1. Adiciona a pergunta do usuário
            appendMessage(prompt, true);
            
            // 2. Mostra indicador de carregamento
            showTypingIndicator();
            
            // 3. Simula tempo de resposta da API (1 a 2 segundos)
            setTimeout(() => {
                removeTypingIndicator();
                
                // 4. Exibe a resposta
                const response = geminiResponses[prompt] || "Interessante pergunta! A arte moderna brasileira é cheia de nuances. Visite a exposição para descobrirmos juntos.";
                appendMessage(response, false);
            }, 1500);
        });
    });

    // 3. Web Speech API (Simulação de Audiodescrição)
    const btnAudioDemo = document.getElementById('btn-audio-demo');
    let isPlaying = false;
    
    // Texto descritivo da exposição Centelhas em Movimento
    const textToSpeak = "Bem vindo ao recurso de audiodescrição. A imagem em destaque mostra a entrada da exposição Centelhas em Movimento, no Espaço Cultural Unifor. Obras icônicas do modernismo brasileiro, com forte presença de tons vibrantes e traços expressivos que marcaram a nossa arte nacional.";

    if ('speechSynthesis' in window) {
        btnAudioDemo.addEventListener('click', () => {
            if (isPlaying) {
                window.speechSynthesis.cancel();
                isPlaying = false;
                btnAudioDemo.innerHTML = '<i class="fas fa-play"></i> Ouvir Demonstração';
            } else {
                const utterance = new SpeechSynthesisUtterance(textToSpeak);
                utterance.lang = 'pt-BR';
                
                utterance.onend = () => {
                    isPlaying = false;
                    btnAudioDemo.innerHTML = '<i class="fas fa-play"></i> Ouvir Demonstração';
                };
                
                window.speechSynthesis.speak(utterance);
                isPlaying = true;
                btnAudioDemo.innerHTML = '<i class="fas fa-stop"></i> Parar Áudio';
            }
        });
    } else {
        btnAudioDemo.style.display = 'none'; // Oculta se o navegador não suportar
    }
});
