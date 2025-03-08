const API_URL = 'http://localhost:8080';

async function login(email, password) {
    try {
        const response = await fetch(`${API_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email,
                password
            })
        });

        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.message || 'Login failed');
        }

        localStorage.setItem('token', data.token);
        localStorage.setItem('username', data.username);

        window.location.href = `/home.html?role=${data.role}`;
    } catch (error) {
        alert(error.message);
    }
}

async function register(name, email, password, role) {
    try {
        const response = await fetch(`${API_URL}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name,
                email,
                password,
                role
            })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Registration failed');
        }

        alert('Registration successful! Please login.');
        document.querySelector('[onclick="openTab(event, \'login\')"]').click();
    } catch (error) {
        alert(error.message);
    }
}