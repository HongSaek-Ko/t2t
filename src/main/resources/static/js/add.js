function toggleTextbox(checkbox) {

    // 1. 텍스트 박스 element 찾기
    const textbox_elem = document.getElementById('quantity');

    // 2-1. 체크박스 선택여부 체크
    // 2-2. 체크박스 선택여부에 따라 텍스트박스 활성화/비활성화
    textbox_elem.disabled = checkbox.checked ? false : true;

    // 3. 텍스트박스 활성화/비활성화 여부에 따라
    // - 텍스트박스가 비활성화 된 경우 : 텍스트박스 초기화
    // - 텍스트박스가 활성화 된 경우 : 포커스 이동
    if(textbox_elem.disabled) { textbox_elem.value = null; }
    else { textbox_elem.focus(); }
}