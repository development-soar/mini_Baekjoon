document.addEventListener('DOMContentLoaded', async () => {
  const area  = document.getElementById('problemListArea');

  const token = localStorage.getItem('jwt');
  if (!token) {
    alert('로그인이 필요합니다.');
    location.href = '/login';
    return;
  }

  try {
    const res = await fetch('/api/baekjoon', {
      method : 'GET',
      headers: token
        ? { 'Authorization': `Bearer ${token}` }
        : {}
    });

    if (res.status === 401) {
      alert('로그인이 만료되었습니다. 다시 로그인해 주세요.');
      localStorage.removeItem('jwt');
      location.href = '/login';
      return;
    }

    if (!res.ok) throw new Error(`(${res.status}) 목록 로딩 실패`);

    const problems = await res.json();

    if (!Array.isArray(problems) || problems.length === 0) {
      area.innerHTML = '<p>등록된 문제가 없습니다.</p>';
      return;
    }

    const rows = problems.map((p, idx) => `
      <tr>
        <td>${idx + 1}</td>
        <td><a href="/problems/${p.baekjoonId}">${p.title}</a></td>
        <td>${p.algorithmCategory ?? ''}</td>
      </tr>
    `).join('');

    area.innerHTML = `
      <table border="1" cellpadding="8">
        <thead>
          <tr><th>No</th><th>제목</th><th>분류</th></tr>
        </thead>
        <tbody>${rows}</tbody>
      </table>
    `;

  } catch (err) {
    console.error(err);
    area.innerHTML = '<p style="color:red">문제 목록을 불러오지 못했습니다.</p>';
  }
});
