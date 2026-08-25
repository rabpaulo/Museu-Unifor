package com.example.mobile.data.mock

import com.example.mobile.data.model.Autor
import com.example.mobile.data.model.Obra

object MockData {

    val autores: List<Autor> = listOf(
        Autor(
            id = "autor_portinari",
            nome = "Cândido Portinari",
            data = "1903 – 1962",
            descricao = "Um dos mais célebres pintores brasileiros, expoente máximo do modernismo e do neorealismo social no Brasil. Suas obras retratam a dor, o trabalho e a poesia do povo brasileiro.",
            image = "sample_paint01"
        ),
        Autor(
            id = "autor_tarsila",
            nome = "Tarsila do Amaral",
            data = "1886 – 1973",
            descricao = "Pintora e desenhista seminal da arte moderna brasileira. Integrante do Grupo dos Cinco e figura central do Movimento Antropofágico, fundou uma nova linguagem visual nacional.",
            image = "sample_paint03"
        ),
        Autor(
            id = "autor_dicavalcanti",
            nome = "Emiliano Di Cavalcanti",
            data = "1897 – 1976",
            descricao = "Pintor, ilustrador e caricaturista carioca, idealizador da Semana de Arte Moderna de 1922. Celebrado por capturar as festas populares, o samba e o cotidiano brasileiro.",
            image = "sample_paint05"
        ),
        Autor(
            id = "autor_anita",
            nome = "Anita Malfatti",
            data = "1889 – 1964",
            descricao = "Pioneira da arte moderna no Brasil. Sua histórica exposição em 1917 provocou os debates que culminaram na Semana de 22, com forte influência expressionista.",
            image = "sample_paint02"
        ),
        Autor(
            id = "autor_lygia",
            nome = "Lygia Clark",
            data = "1920 – 1988",
            descricao = "Pintora e escultora pioneira do Neoconcretismo brasileiro. Rompeu com o espaço tradicional da pintura para criar obras tridimensionais e interativas.",
            image = "sample_paint04"
        ),
        Autor(
            id = "autor_vik",
            nome = "Vik Muniz",
            data = "1961 – Presente",
            descricao = "Artista plástico e fotógrafo brasileiro contemporâneo de renome internacional, conhecido pelo uso experimental de materiais não convencionais como pigmentos, areia e materiais recicláveis.",
            image = "sample_paint06"
        )
    )

    val obras: List<Obra> = listOf(
        Obra(
            id = "obra_retirantes",
            autorId = "autor_portinari",
            autor = "Cândido Portinari",
            nome = "Retirantes",
            data = "1944",
            descricao = "Obra-prima expressionista que retrata com comoção a dura migração de famílias nordestinas fugindo da seca. Apresenta tons terrosos e figuras dramáticas.",
            image = "sample_paint01"
        ),
        Obra(
            id = "obra_menino_piao",
            autorId = "autor_portinari",
            autor = "Cândido Portinari",
            nome = "Menino com Pião",
            data = "1947",
            descricao = "Pintura lírica que evoca a infância no interior de São Paulo, destacando a singeleza e as brincadeiras tradicionais com traços expressivos e geometria sutil.",
            image = "sample_paint02"
        ),
        Obra(
            id = "obra_abaporu",
            autorId = "autor_tarsila",
            autor = "Tarsila do Amaral",
            nome = "Abaporu",
            data = "1928",
            descricao = "Símbolo maior da Antropofagia cultural brasileira. Retrata uma figura monumental com pés gigantescos sobre a terra árida sob um sol radiante e cacto.",
            image = "sample_paint03"
        ),
        Obra(
            id = "obra_a_cuca",
            autorId = "autor_tarsila",
            autor = "Tarsila do Amaral",
            nome = "A Cuca",
            data = "1924",
            descricao = "Pintura vanguardista inspirada no folclore brasileiro, apresentando bichos fantásticos da floresta em formas sinuosas e cores tropicais puras.",
            image = "sample_paint04"
        ),
        Obra(
            id = "obra_samba",
            autorId = "autor_dicavalcanti",
            autor = "Emiliano Di Cavalcanti",
            nome = "Samba",
            data = "1925",
            descricao = "Cena festiva repleta de musicalidade e sensualidade, retratando o universo do samba carioca com rica paleta de cores e dinamismo rítmico.",
            image = "sample_paint05"
        ),
        Obra(
            id = "obra_mulheres_janela",
            autorId = "autor_dicavalcanti",
            autor = "Emiliano Di Cavalcanti",
            nome = "Mulheres na Janela",
            data = "1938",
            descricao = "Representação poética da vida urbana e suburbana, com figuras femininas em repouso e contemplação no casario colonial brasileiro.",
            image = "sample_paint06"
        ),
        Obra(
            id = "obra_estudante",
            autorId = "autor_anita",
            autor = "Anita Malfatti",
            nome = "A Estudante",
            data = "1915",
            descricao = "Retrato expressionista revolucionário que desafiou a estética acadêmica da época, com pinceladas livres, contrastes arrojados e intensa carga psicológica.",
            image = "sample_paint02"
        ),
        Obra(
            id = "obra_bichos",
            autorId = "autor_lygia",
            autor = "Lygia Clark",
            nome = "Bicho - Estrutura",
            data = "1960",
            descricao = "Escultura geométrica em alumínio com placas articuladas por dobradiças, concebida para ser manipulada pelo espectador transformando a arte em experiência viva.",
            image = "sample_paint04"
        ),
        Obra(
            id = "obra_sugar_children",
            autorId = "autor_vik",
            autor = "Vik Muniz",
            nome = "Centelha Contemporânea",
            data = "2010",
            descricao = "Trabalho fotográfico criado a partir de materiais inusitados, questionando a percepção visual e a representação da realidade na arte contemporânea.",
            image = "sample_paint06"
        )
    )
}
