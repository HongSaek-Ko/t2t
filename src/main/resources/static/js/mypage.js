$(document).ready(function () {
    registerEvent(['#rechargeEvent', '#exchageEvent']);
    registerTabEvent('#tabEvent');
});

function registerModalEvent() {
    $('.modal.fade #addMileBtn').on('click', function () {
        $.ajax({
            url: "/member/mypage/charge",
            type: 'POST',
            data: {
                "point": $('.modal.fade.show .modal-body input[type=radio]:checked').val(),
            },
            datatype: 'json'}
        ).done(function (data, textStatus, xhr) {
            if(data.success == true)
                $('#mile').val(data.mile)
            else
                alert('충전 할수 없습니다.');
        });
    });
}

function registerEvent(_target) {
    $.each(_target, function (_i, _el) {
        $(_el).on('click', function (e) {
            var _id = e.target.value;
            console.log(e);
            if (_id == 'rechargeEvent') {
                let element = $($('#rechargeModel').html()).clone();
                $('#commonModal').find('.modal-body').detach();
                $('#commonModal').find('.modal-footer').detach();
                $('#commonModalLabel').text('충전');

                $('#commonModal').find('.modal-content').append($(element));
                $('#commonModal').modal('show');
                registerModalEvent();
            } else if (_id == 'exchageEvent') {
                let element = $($('#exchangeModel').html()).clone();
                $('#commonModal').find('.modal-body').detach();
                $('#commonModal').find('.modal-footer').detach();
                $('#commonModalLabel').text('환전');
                $('#commonModal').find('.modal-content').append($(element));
                $('#commonModal').modal('show');
            }
        });
    });
}

/*
window.addEventListener('DOMContentLoaded',function(){
    showContent('purchase');
});


// 마이페이지 프로필 수정
function modifyProf(input) {
    console.log(input.files[0]);
    let file = input.files[0];
    $('#imgTag').attr("src", URL.createObjectURL(file));
}
*/

//구매/판매 목록 보기 탭
function registerTabEvent(root) {
    $('div[name=tabTradeList]').hide();
    $.each($(root).find('.nav-link'), function(_i, _el){
        $(_el).click(function() {
            $(root).find('.nav-link').removeClass('active');
            $(this).addClass('active');

            if($(this).attr("tabindex") == 1) {
                $('#tab_index_1').show();
                $('#tab_index_2').hide();
            } else {
                $('#tab_index_1').hide();
                $('#tab_index_2').show();
            }
        });
    });
}

//자기소개 글자수 카운팅
$('#myinfotext').keyup(function(e){
    let content = $(this).val();
    $('#lengthCheck').text("(" + content.length + "/최대 200자)"); //실시간 글자수 카운팅
    if(content.length > 200){
        alert("최대 200자 까지만 입력가능합니다");
        $(this).val(content.substring(0,200));
        $('#lengthCheck').text("(200/최대 200자)");
    }

})
$('#modifydone').hide();

// 수정모드로 변경하기
$('#modifyinfo').on('click', function (){
    //readonly 속성 제거
    $('#email').removeAttr("readonly");
    $('#bkAcntName').removeAttr("readonly");
    $('#bankAcnt').removeAttr("readonly");
    $('#modifyinfo').text("수정완료");
    $('#modifyinfo').hide();
    $('#modifydone').show();
});



//마이페이지 수정처리하기
$('#modifydone').on('click', function(){
    console.log("done!!!!!!!!!!!");
    // 컨트롤러로 보내서 MemberDTO로 받을 예정 ->
    // 여기서 보낼 데이터를 MemberDTO 구조에 맞게 JS 객체로 만들어 데이터 체우고
    // Json 문자열로 변경해서 보내기
    let updatedata = {
        email: $('#email').val(),
        bankAcnt: $('#bankAcnt').val(),
        bankNm: $('#bkAcntName').val(),
        usrId: $('#usrID').val()
    }
    $.ajax({
        url: "/member/mypage/modify",
        type: "POST",
        data: JSON.stringify(updatedata),
        contentType: 'application/json;charset=utf-8',
        success: function(result){
            console.log("ajax 요청 성공!!");
            console.log(result);
        },
        error: function (e) {
            console.log("ajax 요청 실패..");
            console.log(e)
        }
    })
});