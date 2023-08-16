from flask import Flask, jsonify
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score

import threading
import time
import pandas as pd

#idade: Representa a idade do cliente em anos.
#premio: Representa o valor do prêmio de seguro pago pelo cliente.
#seguro: Indica se o cliente teve um sinistro de seguro (1) ou não (0)
data = [
    [35, 1000, 1],
    [42, 2500, 1],
    [28, 1800, 0],
    [50, 3200, 1],
    [45, 2800, 1],
]
columns = ['idade', 'premio', 'seguro']
df = pd.DataFrame(data, columns=columns)
X = df[['idade', 'premio']]
y = df['seguro']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

app = Flask(__name__)

@app.route('/aluno', methods=['GET'])
def get_aluno():
    aluno = {
        'nome': 'Maria, Samuel',
        'codigoMatricula': '1278625, 1303214'
    }
    return jsonify(aluno)

@app.route('/')
def home():
    return 'Servidor Python em execução!'

def processar():
    # Simula um processamento demorado
    print('Iniciando processamento...')
    time.sleep(5)
    modelo = LogisticRegression()
    modelo.fit(X_train, y_train)
    
    previsoes = modelo.predict(X_test)
    
    acuracia = accuracy_score(y_test, previsoes)
    media = df['idade'].mean()
    print('Acurácia:', acuracia)
    print('Média de idade:', media)
    print('Processamento concluído!')

@app.route('/processar', methods=['GET'])
def iniciar_processamento():

    threading.Thread(target=processar).start()
    return jsonify({'mensagem': 'Processamento iniciado.'})

if __name__ == '__main__':
    app.run(port=8000)

