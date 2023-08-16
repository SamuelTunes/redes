const express = require('express');
const axios = require('axios');

const app = express();
const port = 3000;

async function getAluno() {
  try {
    const response = await axios.get('http://127.0.0.1:8000/aluno'); 

    const aluno = response.data;
    console.log('Nome:', aluno.nome);
    console.log('Código de Matrícula:', aluno.codigoMatricula);
  } catch (error) {
    console.error('Erro ao obter informações do aluno:', error.message);
  }
}

getAluno();


app.get('/', (req, res) => {
  res.send('Tudo certo!! Utilize o endpoint /processar para iniciar o processamento.');
});

app.post('/processar', async (req, res) => {
  try {
    res.send('Processamento iniciado. Verifique o console para acompanhar o progresso.');

    await axios.get('http://127.0.0.1:8000/processar');

    console.log('Processamento concluído!');
  } catch (error) {
    console.error('Erro ao iniciar o processamento:', error.message);
  }
});

app.listen(port, () => {
  console.log(`Servidor Node.js executando em http://localhost:${port}`);
});
