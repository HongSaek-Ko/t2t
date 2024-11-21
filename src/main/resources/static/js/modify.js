function limitTextBox(checkbox) {

    // 1. 텍스트 박스 element 찾기
    const textbox_elem = document.getElementById('trgtSalQty'); // 판매 수량
    const islimit_elem = document.getElementById('islimit'); // 판매 수량 제한 여부

    // 2-1. 체크박스 선택여부 체크
    // 2-2. 체크박스 선택여부에 따라 텍스트박스 활성화/비활성화
    textbox_elem.disabled = checkbox.checked ? false : true;
    if (textbox_elem.disabled === false) {
        islimit_elem.value = 'Y';
    } else { islimit_elem.value = 'N'; }
    <!--todo: islimit를 Y로 설정하고 작성했다면 수정할 때는 이미 활성화 되어있어야 함. N은 그 반대.  -->
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

function abortTextBox(checkbox) {

    // 1. 텍스트 박스 element 찾기
    const abtReas_elem = document.getElementById('abtReas');
    const salStat_elem = document.getElementById('salStat');

    // 2-1. 체크박스 선택여부 체크
    // 2-2. 체크박스 선택여부에 따라 텍스트박스 활성화/비활성화
    abtReas_elem.disabled = checkbox.checked ? false : true;

    if (abtReas_elem.disabled === false) { // 판매 중지 사유 입력 불가가 FALSE = 활성화 시
        salStat_elem.value = 'PRD02'; // 판매상태 PRD02(판매 중지)로 설정
    } else { salStat_elem.value = 'PRD01'; } // 아니라면 PRD01(판매 중)으로 설정

    // 3. 텍스트박스 활성화/비활성화 여부에 따라
    // - 텍스트박스가 비활성화 된 경우 : 텍스트박스 초기화
    // - 텍스트박스가 활성화 된 경우 : 포커스 이동
    if(abtReas_elem.disabled) {
        abtReas_elem.value = null;
    }
    else {
        abtReas_elem.focus();
    }
}

