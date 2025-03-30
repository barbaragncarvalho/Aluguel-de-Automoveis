const btn = document.getElementById("btnCadastrar");

btn.addEventListener("click", async (ev) => {
    ev.preventDefault();

    const nome = document.getElementById("nome").value
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;
    const cpf = document.getElementById("cpf").value;
    const rg = document.getElementById("rg").value;
    const rua = document.getElementById("rua").value;
    const bairro = document.getElementById("bairro").value;
    const cep = document.getElementById("cep").value;
    const numero = parseInt(document.getElementById("numero").value);
    const opcional = document.getElementById("opcional").value;
    const profissao = document.getElementById("profissao").value;

    const rendimentos = [];
    for (let i = 1; i <= 3; i++) {
        const rendimentoInput = document.getElementById(`rendimento${i}`);
        const entidadeInput = document.getElementById(`entidade${i}`);
        const rendimento = rendimentoInput.value.trim();
        const entidade = entidadeInput.value.trim();

        if (rendimento && entidade) {
            rendimentos.push({
                entidadeEmpregadora: entidade,
                valorRendimento: parseFloat(rendimento),
            });
        }
    }

    const dadosCliente = {
        nome: nome,
        rua: rua,
        bairro: bairro,
        cep: cep,
        numero: numero,
        opcional: opcional,
        email: email,
        senha: senha,
        rg: rg,
        cpf: cpf,
        profissao: profissao,
        rendimentos: rendimentos
    };
    console.log(dadosCliente);
    try {
        const response = await fetch("http://localhost:8080/api/contratantes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(dadosCliente),
        });

        const mensagemDeErro = await response.text();
        if (!response.ok) {
            alert("Erro ao cadastrar o cliente.");
            return;
        }

        alert("Cliente cadastrado com sucesso!");
        document.getElementById("nome").value = "";
        document.getElementById("email").value = "";
        document.getElementById("senha").value = "";
        document.getElementById("cpf").value = "";
        document.getElementById("rg").value = "";
        document.getElementById("rua").value = "";
        document.getElementById("bairro").value = "";
        document.getElementById("cep").value = "";
        document.getElementById("numero").value = "";
        document.getElementById("opcional").value = "";
        document.getElementById("profissao").value = "";

        document.getElementById("rendimento1").value = "";
        document.getElementById("entidade1").value = "";
        document.getElementById("rendimento2").value = "";
        document.getElementById("entidade2").value = "";
        document.getElementById("rendimento3").value = "";
        document.getElementById("entidade3").value = "";
    } catch (error) {
        console.error("Erro na requisição.", error);
        alert("Erro na requisição de cadastro. Tente novamente, por favor ;)");
    }
});
