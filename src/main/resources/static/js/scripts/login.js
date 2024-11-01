function loginJWT() {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  const csrfHeader = document.querySelector(
      'meta[name="_csrf_header"]').getAttribute('content');

  fetch('/api/auth/jwtLogin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      [csrfHeader]: csrfToken
    },
    body: JSON.stringify({
      username: document.querySelector('#username').value,
      password: document.querySelector('#password').value
    })
  }).then(response => {
    return response.json().then(data => ({
      status: response.status,
      body: data
    }));
  }).then(({status, body}) => {
    if (status === 200) {
      document.getElementById('error-message').style.display = 'none';
      window.history.back(); // 로그인 성공 시 이전 페이지로 돌아가기
    } else if (status === 401) {
      document.getElementById('error-message').textContent = body.message
          || "아이디나 비밀번호가 잘못되었습니다.";
      document.getElementById('error-message').style.display = 'block';
    }
  }).catch(error => {
    console.error('로그인 요청 실패:', error);
  });
}