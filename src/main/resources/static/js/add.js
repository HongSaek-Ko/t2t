$(document).ready(function (){
    toggleTextBox(['#trgtSalQty', '#islimit']);
});


function toggleTextBox(checkbox) {

    const textbox_elem = document.getElementById('trgtSalQty');
    const islimit_elem = document.getElementById('islimit');

    textbox_elem.disabled = !checkbox.checked; // 입력칸 활성화 = 체크박스 체크
    if (textbox_elem.disabled === false) { // 입력칸 비활성화가 아님 = 활성화됨
        islimit_elem.value = 'Y'; // 활성화될 경우 '제한여부' 값 'Y'
    } else { islimit_elem.value = 'N'; } // 아니라면 N

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
        document.getElementById('islimit').value = 'Y';
    }
    return true;
}



// 기존 default 프로필 사진을 선택한 사진으로 변경하는 함수 by Moon
function loadFile(input) {
    console.log(input.files[0]);
    let file = input.files[0];
    $('#defaultImg').attr("src", URL.createObjectURL(file));
}