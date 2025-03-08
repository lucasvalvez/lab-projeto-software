document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/index.html';
        return;
    }

    document.getElementById('professorName').textContent = localStorage.getItem('username');
    await loadDisciplinas();
});

async function loadDisciplinas() {
    try {
        const response = await fetch('http://localhost:8080/api/disciplinas/professor', {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });
        const disciplinas = await response.json();
        
        const container = document.getElementById('disciplinas-list');
        container.innerHTML = '';
        
        disciplinas.forEach(disciplina => {
            const div = document.createElement('div');
            div.className = 'disciplina-item';
            div.innerHTML = `
                <h4>${disciplina.nome}</h4>
                <p>Código: ${disciplina.codigo}</p>
                <button onclick="verAlunos(${disciplina.id})">Ver Alunos</button>
            `;
            container.appendChild(div);
        });
    } catch (error) {
        alert('Erro ao carregar disciplinas');
    }
}

async function verAlunos(disciplinaId) {
    try {
        const response = await fetch(`http://localhost:8080/api/disciplinas/${disciplinaId}/alunos`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        });
        const alunos = await response.json();
        
        const modal = document.getElementById('alunos-modal');
        const alunosList = document.getElementById('alunos-list');
        alunosList.innerHTML = '';
        
        alunos.forEach(aluno => {
            const div = document.createElement('div');
            div.className = 'aluno-item';
            div.innerHTML = `
                <p>Nome: ${aluno.nome}</p>
                <p>Matrícula: ${aluno.matricula}</p>
            `;
            alunosList.appendChild(div);
        });
        
        modal.style.display = 'block';
    } catch (error) {
        alert('Erro ao carregar alunos');
    }
}

function handleLogout() {
    localStorage.clear();
    window.location.href = '/index.html';
}

// Modal control
document.querySelector('.close').onclick = function() {
    document.getElementById('alunos-modal').style.display = 'none';
}