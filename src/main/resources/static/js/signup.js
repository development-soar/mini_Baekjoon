document.getElementById('signupForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const formData = {
        userId: this.userId.value,
        username: this.username.value,
        password: this.password.value
    };

    try {
        const response = await fetch('/api/users/signup', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            alert('회원가입 성공! 로그인 페이지로 이동합니다.');
            window.location.href = '/login';
        } else {
            const error = await response.text();
            alert(`회원가입 실패: ${error}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('서버 오류가 발생했습니다.');
    }
});
