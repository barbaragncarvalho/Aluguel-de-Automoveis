// script.js (para pedidos.html)
document.addEventListener('DOMContentLoaded', () => {
    const tabelaPedidos = document.getElementById('tabelaPedidos');
    const clienteInfo = document.querySelector('.informacoes p');
    
    // Buscar pedidos da API
    async function carregarPedidos() {
        try {
            const response = await fetch('http://localhost:8080/api/pedidos');
            const pedidos = await response.json();
            
            // Limpar tabela (exceto cabeçalho)
            const linhasExistentes = tabelaPedidos.querySelectorAll('tr:not(:first-child)');
            linhasExistentes.forEach(linha => linha.remove());
            
            // Preencher tabela
            pedidos.forEach(pedido => {
                const novaLinha = document.createElement('tr');
                
                novaLinha.innerHTML = `
                    <td>${new Date(pedido.data_pedido).toLocaleDateString()}</td>
                    <td>${pedido.automovel.modelo}</td>
                    <td>${pedido.status}</td>
                    <td>
                        ${pedido.status === 'CANCELADO' || pedido.status === 'FINALIZADO' ? 
                            '<button disabled>Cancelar</button>' : 
                            '<button class="btnCancelar">Cancelar</button>'}
                    </td>
                `;
                
                tabelaPedidos.appendChild(novaLinha);
                
                // Adicionar evento de cancelamento
                if (pedido.status === 'EM_ANALISE' || pedido.status === 'APROVADO') {
                    const btnCancelar = novaLinha.querySelector('.btnCancelar');
                    btnCancelar.addEventListener('click', () => cancelarPedido(pedido.id));
                }
            });
            
            // Exibir nome do primeiro cliente (exemplo)
            if(pedidos.length > 0) {
                clienteInfo.textContent = pedidos[0].contratante.nome;
            }
            
        } catch (error) {
            console.error('Erro ao carregar pedidos:', error);
            alert('Erro ao carregar dados dos pedidos');
        }
    }
    
    // Função para cancelar pedido
    async function cancelarPedido(id) {
        if(!confirm('Deseja realmente cancelar este pedido?')) return;
        
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
            
            if(response.ok) {
                alert('Pedido cancelado com sucesso!');
                carregarPedidos(); // Recarregar lista
            } else {
                throw new Error('Falha ao cancelar pedido');
            }
        } catch (error) {
            console.error('Erro:', error);
            alert(error.message);
        }
    }
    
    // Carregar pedidos ao iniciar
    carregarPedidos();
});