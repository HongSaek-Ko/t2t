$(document).ready(function () {
    $(window).on('submit', function() {
        $( "#dialogContent" ).html("수정 완료!")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true
        });
    });
    // registerEvent(['#modifyEvent']);
});

function acceptTrgtSalQty(el) {
    if($(el).prop('checked')) { // is(':checked') 쓰면 N 안 넘어감 (왜인지는 잘 모르겠음)
        $('#islimit').val('Y'); // islimit 값에 Y 부여
        $('#trgtSalQty').show(); // 입력창 나타내기
        console.log( $('#islimit').val()); // Y
    } else {
        $('#islimit').val('N'); // islimit 값에 N 부여
        $('#trgtSalQty').val(''); // 입력창 빈칸으로 초기화 (null로 하면 오류 날 수 있음)
        $('#trgtSalQty').hide(); // 입력창 숨기기
        console.log( $('#islimit').val()); // N
    }
}
//     // 텍스트 박스 선택
//     var textbox_elem = $('#trgtSalQty');
//     var islimit_elem = $('#islimit');
//
//     // 체크박스 선택여부, 체크 여부에 따라 텍스트박스 활성화/비활성화
//     textbox_elem.prop('disabled', !checkbox.checked); // 체크 여부에 따라 활/비활
//     islimit_elem.val(textbox_elem.prop('disabled') ? 'N' : 'Y'); // 텍스트박스가 비활성화되면 'N', 활성화되면 'Y'
//
//     if (textbox_elem.prop('disabled')) { // - 텍스트박스가 비활성화 된 경우 : 텍스트박스 초기화
//         textbox_elem.val(''); // 비활성화되면 값 초기화
//     } else { // - 텍스트박스가 활성화 된 경우 : 포커스 이동
//         textbox_elem.focus(); // 활성화되면 포커스
//     }
// }
function abortTextBox(checkbox) {
    var salStat_elem = $('#salStat'); // 판매 상태 (중지 여부)
    var abtReas_elem = $('#abtReas'); // 중지 사유

    abtReas_elem.prop('disabled', !checkbox.checked);
    salStat_elem.val(abtReas_elem.prop('disabled') ? 'PRD01' : 'PRD02'); // 취소사유 비활성화 시 판매상태 '판매중'...

    // if (abtReas_elem.disabled === false) { // 판매 중지 사유 입력 불가가 FALSE = 활성화 시
    //     salStat_elem.value = 'PRD02'; // 판매상태 PRD02(판매 중지)로 설정
    // } else { salStat_elem.value = 'PRD01'; } // 아니라면 PRD01(판매 중)으로 설정
    if (abtReas_elem.prop('disabled')) {
        abtReas_elem.val('');
    } else {
        abtReas_elem.focus();
    }
}

$("#modifyForm").validate({
    rules: {
        title: {required: true, minlength: 2, maxlength: 20},
        cont: {required: true, maxlength: 200},
        cate: {required: true},
        price: {required: true},
        trgtSalQty: {required: true},
        abtReas: {required: true, maxlength: 20}
    },
    messages: {
        title: "제목은 최소 2자, 최대 20자 입니다.",
        cont: "200자 이내의 설명을 입력해주세요.",
        cate: "카테고리를 선택해주세요.",
        price: "가격을 설정해주세요.",
        trgtSalQty: "판매 수량을 설정해주세요. (체크 해제 시 수량 제한(-), 설정 불필요)",
        abtReas: "중지 사유를 입력해주세요. (최대 20자)"

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
// function registerEvent(_target) {
//     $.each(_target, function (_i, _el) {
//         $(_el).on('click', function (e) {
//             var _id = e.target.value;
//             if(_id === 'modifyEvent') {
//                 $('#modifyPrdModal').find('.modal-body').empty(); // 모달 새로 열 때마다 기존 것 비워줘야하므로...
//                 $('#modifyPrdModal').find('.modal-body').append(createModifyLayout()); // 모달에 이거 추가...
//                 $('#modifyPrdModalLabel').text('수정');
//                 $('#modifyPrdModal').modal('show');
//             }
//         });
//     });
// }
// function createModifyLayout() {
//     var modifyArr = [];
//     modifyArr.push('<form id="modifyPrdForm" action="/product/'+ $('#modifyEvent').data('prdid') +'/modify" method="post">') // <...action="/product/prdId/delete"...>
//     modifyArr.push('    <div class="mb-3" style="display: flex; justify-content: center">')
//     modifyArr.push('        <h4> 수정하시겠습니까? </h4>')
//     modifyArr.push('    </div>')
//     modifyArr.push('    <div class="mb-2" style="display: flex; justify-content: center">')
//     modifyArr.push('        <button type="submit" class="btn btn-danger mx-2" id="modifyPrdComplete" >수정</button>')
//     modifyArr.push('    </div>')
//     modifyArr.push('</form>')
//
//     return modifyArr.join('');
// }