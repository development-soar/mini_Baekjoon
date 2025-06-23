document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const response = await fetch('/api/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            userId: this.userId.value,
            password: this.password.value
        })
    });

    if (response.ok) {
        const token = await response.text();
        localStorage.setItem('jwt', token);
        alert('로그인 성공!');
        window.location.href = '/';      } else {
        const error = await response.text();
        alert(`로그인 실패: ${error}`);
    }
});
