document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const urlParams = new URLSearchParams(window.location.search);
    const pedidoId = urlParams.get('pedidoId');

    if (!pedidoId) {
        alert('Pedido não identificado! Redirecionando...');
        window.location.href = '../pedidosagente/pedidos.html';
        return;
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = {
            dataAnalise: document.getElementById('data').value,
            resultado: document.getElementById('resultado').value,
            observacao: document.getElementById('observacoes').value
        };

        if (!formData.resultado || formData.resultado === '') {
            alert('Selecione um resultado para a avaliação!');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/avaliacoes?pedidoId=${pedidoId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                alert('Avaliação registrada com sucesso!');
                window.location.href = '../pedidosagente/pedidos.html';
            } else {
                const error = await response.json();
                alert(`Erro: ${error.message || 'Falha no registro da avaliação'}`);
            }
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro de conexão com o servidor');
        }
    });
});