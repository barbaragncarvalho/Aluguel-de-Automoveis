document.addEventListener('DOMContentLoaded', async () => {
    const botaoClientes = document.querySelector('a[href*="clientes"] button');
    const botaoAgentes = document.querySelector('a[href*="agentes"] button');

    try {
        // Buscar contagens da API
        const [respostaClientes, respostaAgentes] = await Promise.all([
            fetch('http://localhost:8080/api/clientes/count'),
            fetch('http://localhost:8080/api/agentes/count')
        ]);

        // Extrair valores numéricos
        const totalClientes = await respostaClientes.json();
        const totalAgentes = await respostaAgentes.json();

        // Atualizar textos dos botões
        botaoClientes.textContent = `Clientes (CPF) - ${totalClientes}`;
        botaoAgentes.textContent = `Agentes (CNPJ) - ${totalAgentes}`;

    } catch (error) {
        console.error("Erro na integração:", error);
        // Fallback: Mantém texto original em caso de erro
        botaoClientes.textContent = "Clientes (CPF)";
        botaoAgentes.textContent = "Agentes (CNPJ)";
    }
});