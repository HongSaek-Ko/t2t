$(document).ready(function (){
    toggleTextBox(['#trgtSalQty', '#islimit']);
    $(window).on('submit', function() {
        $( "#dialogContent" ).html("수정 완료!")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true
        });
    });
});
$("#productForm").validate({
    rules: {
        title: {required: true, minlength: 2, maxlength: 20},
        cont: {required: true, maxlength: 200},
        cate: {required: true},
        price: {required: true},
        imgFile: {required: true},
        trgtSalQty: {required: true, minlength: 0}
    },
    messages: {
        title: "제목은 최소 2글자, 최대 20글자 입니다.",
        cont: "200글자 이내의 설명을 입력해주세요.",
        cate: "카테고리를 선택해주세요.",
        price: "가격을 설정해주세요.",
        imgFile: "상품 이미지를 올려주세요.",
        trgtSalQty: "판매 수량을 설정해주세요. (체크 해제 시 수량 제한(-), 설정 불필요)"
    },
    errorElement: "div",
    errorPlacement: function (error, element) {
        error.addClass("invalid-feedback");
        error.insertAfter(element);
    },
    highlight: function (element) {
        $(element).removeClass('is-valid').addClass('is-invalid');
    },
    unhighlight: function (element) {
        $(element).removeClass('is-invalid').addClass('is-valid');
    }
});



// 태그 넣는 코드 시작
const input = document.querySelector('input[name=tags]');
const tagify = new Tagify(input);

tagify.on('add', function (e) {
    console.log('Tag added:', e.detail.data.value);
});

tagify.on('remove', function (e) {
    console.log('Tag removed:', e.detail.data.value);
});

function changingStatus() {
    let curatorStatus = $('#curatorStatus').val();
    if (curatorStatus) {
        const currentUrl = window.location.href;
        location.href = updateUrlParameter(currentUrl, 'curatorStatus', curatorStatus);
    } else {
        const urlObject = new URL(window.location.href);
        urlObject.searchParams.delete('curatorStatus');
        location.href = urlObject.toString();
    }
}

function toggleTextBox(checkbox) {

    const textbox_elem = document.getElementById('trgtSalQty');
    const islimit_elem = document.getElementById('islimit');

    textbox_elem.disabled = !checkbox.checked; // 입력칸 활성화 = 체크박스 체크
    if (textbox_elem.disabled === false) { // 입력칸 비활성화가 아님 = 활성화됨
        islimit_elem.value = 'Y'; // 활성화될 경우 '제한여부' 값 'Y'
    } else { islimit_elem.value = 'N'; } // 아니라면 N

    if(textbox_elem.disabled) {
        textbox_elem.value = null;
    } else {
        textbox_elem.value = '';
        textbox_elem.focus();
    }
}
// function fieldCheck() {
//
//     let form = document.getElementById('form')
//     if(form.hashTag.value === ''
//         || form.price.value === ''
//         || form.cate.value === ''
//         || form.cont.value === ''
//         || form.imgFile.value === ''
//         || form.title.value === '') {
//         alert("필수 입력사항을 입력해주세요.")
//         return false;
//     }
//     if(form.trgtSalQty.value === '') {
//         if(form.limitSwitch.checked) {
//             alert("구매 제한 수량을 입력하세요.");
//             return false;
//         }
//     }else{
//         document.getElementById('islimit').value = 'Y';
//     }
//     return true;
// }

// jQuery Validation



// 기존 default 프로필 사진을 선택한 사진으로 변경하는 함수 by Moon
function loadFile(input) {
    console.log(input.files[0]);
    let file = input.files[0];
    $('#defaultImg').attr("src", URL.createObjectURL(file));
}