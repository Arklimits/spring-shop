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
async function uploadAndSubmit(event) {
  event.preventDefault();

  const fileInput = document.getElementById('image');
  const file = fileInput.files[0];

  if (!file) {
    document.getElementById('uploadForm').submit();
    return;
  }

  const fileName = encodeURIComponent(file.name);

  const response = await fetch(`/api/item/presigned-url?filename=${fileName}`);
  const presignedUrl = await response.text();

  const uploadResult = await fetch(presignedUrl, {
    method: 'PUT',
    headers: {
      'Content-Type': file.type
    },
    body: file
  });

  if (uploadResult.ok) {
    const imageUrl = presignedUrl.split("?")[0];
    document.getElementById('imageUrl').value = imageUrl;

    document.getElementById('uploadForm').submit();
  } else {
    console.error("파일 업로드 실패", uploadResult);
    alert("파일 업로드에 실패했습니다.");
  }

  return false;
}