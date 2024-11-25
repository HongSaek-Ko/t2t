$(document).ready(function (){
    var isLimitValue = $('islimit').val();
    if(isLimitValue === 'Y') {
        $('#limitSwitch').prop('checked', true);
    } else {
        $('#limitSwitch').prop('checked', false);
    }
});

function limitTextBox(checkbox) {

    // 텍스트 박스 선택
    var textbox_elem = $('#trgtSalQty');
    var islimit_elem = $('#islimit');

    // 체크박스 선택여부, 체크 여부에 따라 텍스트박스 활성화/비활성화
    textbox_elem.prop('disabled', !checkbox.checked); // 체크 여부에 따라 활/비활
    islimit_elem.val(textbox_elem.prop('disabled') ? 'N' : 'Y'); // 텍스트박스가 비활성화되면 'N', 활성화되면 'Y'

    if (textbox_elem.prop('disabled')) { // - 텍스트박스가 비활성화 된 경우 : 텍스트박스 초기화
        textbox_elem.val(''); // 비활성화되면 값 초기화
    } else { // - 텍스트박스가 활성화 된 경우 : 포커스 이동
        textbox_elem.focus(); // 활성화되면 포커스
    }
}

function abortTextBox(checkbox) {

    var abtReas_elem = $('#abtReas');
    var salStat_elem = $('#salStat');

    abtReas_elem.prop('disabled', !checkbox.checked);
    salStat_elem.val(abtReas_elem.prop('disabled') ? 'PRD01' : 'PRD02'); // 취소사유 비활성화 시 판매상태 '판매중'...

    // if (abtReas_elem.disabled === false) { // 판매 중지 사유 입력 불가가 FALSE = 활성화 시
    //     salStat_elem.value = 'PRD02'; // 판매상태 PRD02(판매 중지)로 설정
    // } else { salStat_elem.value = 'PRD01'; } // 아니라면 PRD01(판매 중)으로 설정

    if(abtReas_elem.prop('disabled')) {
        abtReas_elem.val('');
    } else {
        abtReas_elem.focus();
    }
}

