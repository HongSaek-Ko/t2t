function toggleTextBox(checkbox) {

    // 1. 텍스트 박스 element 찾기
    const textbox_elem = document.getElementById('trgtSalQty');
    const islimit_elem = document.getElementById('islimit');

    // 2-1. 체크박스 선택여부 체크
    // 2-2. 체크박스 선택여부에 따라 텍스트박스 활성화/비활성화
    textbox_elem.disabled = checkbox.checked ? false : true;
    <!--todo: islimit 체크박스 누름과 동시에 수량 추가? 아니면 html 수준에서 작성 안하면 작성하라고 알림? 택1...-->
    if (textbox_elem.disabled === false) {
        islimit_elem.value = 'Y';
    } else { islimit_elem.value = 'N'; }

    // 3. 텍스트박스 활성화/비활성화 여부에 따라
    // - 텍스트박스가 비활성화 된 경우 : 텍스트박스 초기화
    // - 텍스트박스가 활성화 된 경우 : 포커스 이동
    if(textbox_elem.disabled) {
        textbox_elem.value = null;
    }
    else {
        textbox_elem.focus();
    }
}

function fieldCheck() {

    let form = document.getElementById('form')
    let trgtSalqty = document.getElementById('trgtSalQty');

    if(form.limitSwitch.checked) {
        if (trgtSalqty.value === ''){
            alert("구매 제한 수량을 입력하세요.");
            return false;
        }
    } else {
        document.getElementById('islimit').value = 'N';
    }
    return true;
}