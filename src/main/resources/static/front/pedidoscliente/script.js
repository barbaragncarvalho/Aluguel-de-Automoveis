document.addEventListener('DOMContentLoaded', () => {
    const tabelaPedidos = document.getElementById('tabelaPedidos');
    const clienteInfo = document.querySelector('.informacoes p');
    
    const clienteId = localStorage.getItem('clienteId');
    const clienteNome = localStorage.getItem('clienteNome');

    if (clienteNome) {
        clienteInfo.textContent = clienteNome;
    }

    // Carregar pedidos da API
    async function carregarPedidos() {
        if (!clienteId) {
            alert('Cliente não identificado!');
            return;
        }
        try {
            const response = await fetch(`http://localhost:8080/api/pedidos/cliente/${clienteId}`);
            const pedidos = await response.json();
            
            // Limpar tabela (exceto cabeçalho)
            const linhasExistentes = tabelaPedidos.querySelectorAll('tr:not(:first-child)');
            linhasExistentes.forEach(linha => linha.remove());
            
            // Preencher tabela dinamicamente
            pedidos.forEach(pedido => {
                const novaLinha = document.createElement('tr');
                
                novaLinha.innerHTML = `
                    <td>${formatarData(pedido.data_pedido)}</td>
                    <td>${pedido.automovel.marca} ${pedido.automovel.modelo}</td>
                    <td>${traduzirStatus(pedido.status)}</td>
                    <td>
                        ${pedido.status === 'CANCELADO' ? 
                            '<button disabled>Cancelado</button>' : 
                            '<button class="btnCancelar">Cancelar</button>'}
                    </td>
                `;
                
                tabelaPedidos.appendChild(novaLinha);
                
                // Adicionar evento de cancelamento
                if (pedido.status !== 'CANCELADO') {
                    const btnCancelar = novaLinha.querySelector('.btnCancelar');
                    btnCancelar.addEventListener('click', () => cancelarPedido(pedido.id));
                }
            });
            
            // Atualizar nome do cliente (exemplo com primeiro pedido)
            if (pedidos.length > 0) {
                clienteInfo.textContent = pedidos[0].contratante.nome;
            }
            
        } catch (error) {
            console.error('Erro ao carregar pedidos:', error);
            alert('Falha ao carregar dados!');
        }
    }

    // Função para cancelar pedido
    async function cancelarPedido(id) {
        if (!confirm('Tem certeza que deseja cancelar este pedido?')) return;
        
        try {
            const response = await fetch(`http://localhost:8080/api/pedidos/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    status: 'CANCELADO'
                })
            });
            
            if (response.ok) {
                alert('Pedido cancelado!');
                carregarPedidos(); // Recarregar a lista
            } else {
                throw new Error('Erro ao cancelar pedido');
            }
        } catch (error) {
            console.error('Erro:', error);
            alert(error.message);
        }
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