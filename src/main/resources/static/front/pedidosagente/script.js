document.addEventListener('DOMContentLoaded', () => {
    const tabelaPedidos = document.getElementById('tabelaPedidos');
    const tabelaAvaliados = document.getElementById('tabelaPedidosAvaliados');

    async function carregarPedidos() {
        const agenteId = localStorage.getItem('proprietarioId'); 
        if (!agenteId) {
            alert('Agente não identificado!');
            return;
        }

        try {
            const responseAutomoveis = await fetch(`http://localhost:8080/api/automoveis/proprietario/${agenteId}`);
            const automoveis = await responseAutomoveis.json();
            const idsAutomoveis = automoveis.map(auto => auto.id);

            const responsePedidos = await fetch('http://localhost:8080/api/pedidos');
            const todosPedidos = await responsePedidos.json();

            const pedidosFiltrados = todosPedidos.filter(pedido =>
                idsAutomoveis.includes(pedido.automovel.id) &&
                pedido.status !== 'CANCELADO'
            );

            limparTabela(tabelaPedidos);
            limparTabela(tabelaAvaliados);

            const emAnalise = pedidosFiltrados.filter(p => p.status === 'EM_ANALISE');
            const avaliados = pedidosFiltrados.filter(p =>
                p.status === 'APROVADO' || p.status === 'REPROVADO'
            );

            preencherTabela(tabelaPedidos, emAnalise, true); 
            preencherTabela(tabelaAvaliados, avaliados, false); 
        } catch (error) {
            console.error('Erro ao carregar pedidos:', error);
            alert('Falha ao carregar dados!');
        }
    }

    function limparTabela(tabela) {
        const linhas = tabela.querySelectorAll('tr:not(:first-child)');
        linhas.forEach(linha => linha.remove());
    }

    function preencherTabela(tabela, pedidos, exibirBotaoAvaliar) {
        pedidos.forEach(pedido => {
            const novaLinha = document.createElement('tr');
            novaLinha.innerHTML = `
                <td>${pedido.contratante.nome}</td>
                <td>${formatarData(pedido.data_pedido)}</td>
                <td>${pedido.automovel.marca} ${pedido.automovel.modelo}</td>
                <td>${traduzirStatus(pedido.status)}</td>
                ${exibirBotaoAvaliar ?
                    `<td><a href="../avaliarpedido/avaliar.html?pedidoId=${pedido.id}">
                        <button class="btnAvaliar">Avaliar</button>
                    </a></td>` :
                    '<td></td>'}
            `;
            tabela.appendChild(novaLinha);
        });
    }

    // Utilitários
    function formatarData(dataString) {
        const data = new Date(dataString);
        return data.toLocaleDateString('pt-BR');
    }

    function traduzirStatus(status) {
        const traducoes = {
            'EM_ANALISE': 'Em análise',
            'APROVADO': 'Aprovado',
            'CANCELADO': 'Cancelado'
        };
        return traducoes[status] || status;
    }

    // Inicialização
    carregarPedidos();
});