// CSRF 토큰을 JavaScript 변수에 저장
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
    'content');
const csrfHeader = document.querySelector(
    'meta[name="_csrf_header"]').getAttribute('content');

async function checkUsername() {
  const username = document.getElementById("username").value;
  const messageElem = document.getElementById("username-message");

  if (!username) {
    messageElem.innerText = "아이디를 입력하세요.";
    return false;
  }

  try {
    const response = await fetch(
        `/api/member/check-username?username=${username}`);
    if (response.status === 409) {
      messageElem.innerText = "이미 사용 중인 사용자 이름입니다.";
      return false;
    } else if (response.ok) {
      messageElem.innerText = "사용 가능한 아이디입니다.";
      return true;
    } else {
      messageElem.innerText = "아이디 확인 중 오류가 발생했습니다.";
      return false;
    }
  } catch (error) {
    console.error('Error:', error);
    messageElem.innerText = "네트워크 오류가 발생했습니다.";
    return false;
  }
}

async function validateForm(event) {
  event.preventDefault(); // 기본 제출 동작 방지

  const isUsernameAvailable = await checkUsername();
  if (!isUsernameAvailable) {
    return false;
  }

  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  // 비밀번호 확인
  if (password !== confirmPassword) {
    alert("비밀번호가 일치하지 않습니다."); // 비밀번호 불일치 경고
    return false;
  }

  const formData = {
    displayName: document.getElementById("displayName").value,
    username: document.getElementById("username").value,
    password: password // 확인된 비밀번호 사용
  };

  try {
    const response = await fetch('/api/member/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json', // JSON 형식으로 데이터 전송
        [csrfHeader]: csrfToken // CSRF 토큰 헤더에 추가
      },
      body: JSON.stringify(formData) // 데이터를 JSON 문자열로 변환
    });

    if (response.ok) {
      alert('등록 완료!');
      window.location.href = '/login'; // 로그인 페이지로 리다이렉트
    } else {
      const errorMessage = await response.text();
      alert(errorMessage); // 서버에서 보낸 오류 메시지 표시
    }
  } catch (error) {
    console.error('Error:', error);
    alert('등록 중 오류가 발생했습니다.');
  }
}
