function toggleTextBox(checkbox) {

    // 1. 텍스트 박스 element 찾기
    const textbox_elem = document.getElementById('trgtSalQty');
    const islimit_elem = document.getElementById('islimit');

    // 2-1. 체크박스 선택여부 체크
    // 2-2. 체크박스 선택여부에 따라 텍스트박스 활성화/비활성화
    textbox_elem.disabled = checkbox.checked ? false : true;
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

    if(form.hashTag.value === ''
        || form.price.value === ''
        || form.cate.value === ''
        || form.cont.value === ''
        || form.imgFile.value === ''
        || form.title.value === '') {
        alert("필수 입력사항을 입력해주세요.")
        return false;
    }

    if(!form.limitSwitch.checked) {
        return trgtSalqty.value
    }

    if(form.trgtSalQty.value === '') {
        if(form.limitSwitch.checked) {
            alert("구매 제한 수량을 입력하세요.");
            return false;
        }
    }else{
        document.getElementById('islimit').value = 'N';
    }
    return true;
}
// 기존 default 프로필 사진을 선택한 사진으로 변경하는 함수 by Moon
function loadFile(input) {
    console.log(input.files[0]);
    let file = input.files[0];
    $('#defaultImg').attr("src", URL.createObjectURL(file));
}