document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('jwt');
    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login';
        return;
    }

    try {
        const response = await fetch('/api/users/mypage', {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            if (response.status === 401) {
                alert('세션이 만료되었습니다. 다시 로그인하세요.');
                localStorage.removeItem('jwt');
                window.location.href = '/login';
            } else {
                throw new Error('서버 응답 오류');
            }
            return;
        }

        const data = await response.json();
        displayUserInfo(data.userInfo);
        displaySubmissions(data.submissions);
    } catch (error) {
        console.error('Error:', error);
        alert('마이페이지 정보를 불러오는 중 오류가 발생했습니다.');
    }
});

// 사용자 정보 표시 함수 (추가 필수)
function displayUserInfo(userInfo) {
    const userInfoElement = document.getElementById('userInfo');

    // LocalDateTime을 JavaScript Date로 변환
    const createdAt = new Date(userInfo.createdAt);
    const formattedDate = `${createdAt.getFullYear()}년 ${createdAt.getMonth()+1}월 ${createdAt.getDate()}일`;

    userInfoElement.innerHTML = `
        <p><strong>아이디:</strong> ${userInfo.userId}</p>
        <p><strong>이름:</strong> ${userInfo.username}</p>
        <p><strong>가입일:</strong> ${formattedDate}</p>
    `;
}

// 제출 기록 표시 함수 (기존 코드 유지)
function displaySubmissions(submissions) {
    const tbody = document.getElementById('submissionTable');
    tbody.innerHTML = '';

    if (!submissions || submissions.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" class="no-records">
                    문제를 푼 기록이 없습니다.
                </td>
            </tr>
        `;
        return;
    }

    submissions.forEach((sub, index) => {
        const submittedAt = new Date(sub.submittedAt);
        const formattedDate = `${submittedAt.getFullYear()}-${(submittedAt.getMonth()+1).toString().padStart(2, '0')}-${submittedAt.getDate().toString().padStart(2, '0')} 
        ${submittedAt.getHours().toString().padStart(2, '0')}:${submittedAt.getMinutes().toString().padStart(2, '0')}`;

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${sub.title}</td> <!-- 문제 제목! -->
            <td>${formattedDate}</td>
            <td>${sub.executionTime} ms</td>
            <td class="${sub.result === 'correct' ? 'correct' : 'wrong'}">
                ${sub.result === 'correct' ? '정답' : '오답'}
            </td>
        `;
        tbody.appendChild(row);
    });
}