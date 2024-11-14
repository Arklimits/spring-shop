// 미리보기 함수
function previewImage(e) {
  if (e.files && e.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      document.getElementById('imagePreview').src = e.target.result;
    }
    reader.readAsDataURL(e.files[0]);
  }
}

// 파일 업로드와 폼 제출 처리
async function uploadAndSubmit(event, method = 'POST') {
  console.log("폼 제출 시작");
  event.preventDefault();

  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  const csrfHeader = document.querySelector(
      'meta[name="_csrf_header"]').getAttribute('content');

  const fileInput = document.getElementById('image');
  const file = fileInput.files[0];

  if (file) {
    console.log("파일 업로드 시작");
    const fileName = encodeURIComponent(file.name);
    const response = await fetch(
        `/api/item/presigned-url?filename=${fileName}`);
    const presignedUrl = await response.text();

    const uploadResult = await fetch(presignedUrl, {
      method: 'PUT',
      headers: {
        'Content-Type': file.type
      },
      body: file
    });

    if (uploadResult.ok) {
      console.log("파일 업로드 성공");
      const imageUrl = presignedUrl.split("?")[0];
      document.getElementById('imageUrl').value = imageUrl;
    } else {
      console.error("파일 업로드 실패", uploadResult);
      alert("파일 업로드에 실패했습니다.");
      return;
    }
  }

  console.log("서버로 폼 데이터 전송 시작");
  const formData = new FormData(document.getElementById('uploadForm'));
  const data = {};
  formData.forEach((value, key) => {
    data[key] = value;
  });

  fetch('/api/item', {
    method: method,
    headers: {
      'Content-Type': 'application/json',
      [csrfHeader]: csrfToken
    },
    body: JSON.stringify(data)
  })
  .then((response) => {
    if (response.ok) {
      console.log("폼 제출 성공");
      window.location.href = '/list';  // 요청이 성공하면 목록 페이지로 이동
    } else {
      console.error("폼 제출 실패", response);
      alert("폼 제출에 실패했습니다.");
    }
  })
  .catch((error) => {
    console.error("요청 중 오류 발생:", error);
    alert("요청 중 오류가 발생했습니다.");
  });
}
