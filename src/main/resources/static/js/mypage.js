//충전, 환전 클릭시 이벤트
//구매목록, 판매목록 탭 전환 이벤트
//비밀번호 수정 모달환면 띄우기
//비밀번호 일치 여부 확인 이벤트
$(document).ready(function () {
    registerEvent(['#rechargeEvent', '#exchageEvent']);
    registerTabEvent('#tabEvent');
    passwordcheckEvent();
    passwdModify();
    modifyProfile();


});


$("#modifyForm").validate({
    rules: {
        //html name 값 : validate
        email: {required: true},
        bankAcntOwr : {required: true},
        bankAcnt: {required: true},

    },
    messages: {
        email: "이메일을 입력하세요.",
        bankAcntOwr : "예금주 명을 입력하세요",
        bankAcnt: "계좌번호를 입력하세요"

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


//Ajax 충전버튼 클릭시 이벤트
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
            console.log(data.success);
            if(data.success) {
                $('#mile').val(data.mile);
                console.log(data.mile);
                $('#commonModal').modal('hide');
            }
            else
                alert('충전 할수 없습니다.');
        });

    });

}

//Ajax 환전버튼 클릭시 이벤트
function exchangeModalEvent() {
    $('.modal.fade #exchangeMileBtn').on('click', function () {
        //내가 가지고 있는 mile에서 환전할 mile 뺀 값이 0보다 클 때 환전가능
        if(!(parseInt($('#mile').val()) - parseInt($('#exchangeMile').val()) < 0)) {
            $.ajax({
                url: "/member/mypage/excharge",
                type: 'POST',
                data: {
                    "point": $('.modal.fade.show input[name=exchange]').val(),
                },
                datatype: 'json'}
            ).done(function (data, textStatus, xhr) {
                console.log(data);
                console.log("환전 값", data.success); // true
                if(data.success == true) {
                    $('#commonModal').modal('hide');
                    $('#mile').val(data.mile);

                }
                else
                    alert('환전 할수 없습니다.');

            });
        } else {
            //마일리지가 -가 되면 환전할 마일리지 0으로 리셋
            alert("출금 마일리지보다 보유 마일리지가 적습니다. 다시 입력 해주세요.");
            $('#exchangeMile').val("0");
        }
    });

}

//모달 찾아서 바디, 풋터 비우기
function clearModal(title) {
    $('#commonModal').find('.modal-body').empty().remove();
    $('#commonModal').find('.modal-footer').empty().remove();
    $('#commonModalLabel').text(title);
}

//비밀번호 수정하기 모달
function passwordcheckEvent(){
    //비밀번호 수정하기 버튼 클릭시
    $('#modifyPasswdModal').on('click', function(){
        let element = $($('#passwordcheckModel').html()).clone();
        clearModal('비밀번호 변경하기');
        $('#commonModal').find('.modal-content').append($(element));
        $('#commonModal').modal('show');
        passwdModify();
    });
}


//비밀번호 변경하기
function passwdModify() {
    $('.modal.fade #modifyPassword').on('click', function () {
        if($('#modalPasswd').val() !== $('#modalPasswdCheck').val()) {
            alert('비밀번호가 일치하지 않습니다.');
            //비밀번호가 일치하지 않을 경우 리셋
            $('#modalPasswd').val('');
            $('#modalPasswdCheck').val('');
        } else {
            $.ajax({
                url: "mypage/modify/passswd",
                type: 'POST',
                data: {
                    "passwd": $('.modal.fade.show input[name=modalPasswd]').val()
                },
                datatype: 'json'}
            ).done(function (data, textStatus, xhr) {
                console.log($('.modal.fade.show input[name=modalPasswd]').val()); //1234
                console.log($('.modal.fade.show input[name=modalPasswdCheck]').val()); //1234
                alert("비밀번호가 변경되었습니다.");
                $('#commonModal').modal('hide');

            });
        }
    });

}


//충전, 환전 모달환면 전환 이벤트
// _i : index
// _el : element 요소
// _target : 배열 []
// _id : value - rechargeEvent, exchangeEvent

function registerEvent(_target) {
    $.each(_target, function (_i, _el) {
        $(_el).on('click', function (e) {
            var _id = e.target.value;
            console.log(e);
            if (_id == 'rechargeEvent') {
                let element = $($('#rechargeModel').html()).clone();
                clearModal('충전');
                $('#commonModal').find('.modal-content').append($(element));
                $('#commonModal').modal('show');
                $('#mileModal').val($('#mile').val());
                registerModalEvent();
            } else if (_id == 'exchageEvent') {
                let element = $($('#exchangeModel').html()).clone();
                clearModal('환전');
                $('#commonModal').find('.modal-content').append($(element));
                $('#commonModal').modal('show');
                $('#mileModal').val($('#mile').val());
                exchangeModalEvent();
            }
        });
    });
}


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
$('#myInfoText').keyup(function(e){
    let content = $(this).val();
    $('#lengthCheck').text("(" + content.length + "/최대 200자)"); //실시간 글자수 카운팅
    if(content.length > 200){
        alert("최대 200자 까지만 입력가능합니다");
        $(this).val(content.substring(0,200));
        $('#lengthCheck').text("(200/최대 200자)");
    }

})

//수정완료, 수정취소 버튼 숨겨놓기
$('#modifyDone').hide();
$('#modifyCancel').hide();

//비밀번호 수정버튼 숨기기
$('#modifyPasswdModal').hide();

//프로필 변경 수정 숨기기
$('#profileForm').hide();

// 수정모드로 변경하기
$('#modifyInfo').on('click', function (){
    //readonly 속성 제거
    $('#email').removeAttr("readonly");   // readonly 속성 제거
    $('#bankAcntOwr').removeAttr("readonly");
    $('#bankAcnt').removeAttr("readonly");
    $('#myInfoText').removeAttr("readonly");
    $('#profileForm').removeAttr("readonly").show();
    $('#modifyPasswdModal').show();
    $('#modifyInfo').text("수정완료").hide();

    $('#modifyDone').show(); //수정완료 버튼 보여주기
    $('#modifyCancel').show(); //취소버튼 보여주기
});

//마이페이지 수정처리하기
$('#modifyDone').on('click', function(){
    console.log("done!!!!!!!!!!!");
    let _arr = $('#modifyForm').find('input[required], textarea[required]')
    let formData = new FormData();
    $.each(_arr, function(index, element){
        if($(element).attr('type') === 'file') {
            if($(element).val() !== '')
                formData.append("imageProfile",$(element)[0].files[0]);
        } else {
            if($(element).attr('name'))
                formData.append("" + $(element).attr('name'), $(element).val());
        }
    });

    $.ajax({
        url: "/member/mypage/modify",
        type: "POST",
        data: formData,
        processData : false,
        contentType : false,
        //enctype: ,
        //contentType: 'multipart/form-data',
        //map에서 넘어온 값 result로 받아줌
        success: function(result){
            console.log("ajax 요청 성공!!");
            alert("수정이 완료되었습니다");
            //jqAlert("수정이 완료되었습니다!!!", "수정 완료",
            //    function(){ window.location.href="/member/mypage" });

            console.log(result.findUser.usrId);

            $('#email').attr('readonly',true);
            $('#myInfoText').attr('readonly',true);
            $('#bankNm').attr('readonly',true);
            $('#bankAcnt').attr('readonly',true);
            $('#bankAcntOwr').attr('readonly',true);
            $('#profileForm').hide();

            $('#modifyDone').hide(); //수정완료 버튼 숨기기
            $('#modifyCancel').hide(); //취소버튼 숨기기

            //$( "#notify_dialog #nofiy_cont").html("수정이 완료 되었습니다.");
            // $( "#notify_dialog" ).dialog('open');
            //수정완료 후 페이지 되돌아가기
            window.location.href="/member/mypage";
            //window.location.reload();

            // 기존 default 프로필 사진을 선택한 사진으로 변경하는 함수 by Moon
            function loadFile(input) {
                console.log(input.files[0]);
                let file = input.files[0];
                $('#defaultImg').attr("src", URL.createObjectURL(file));
            }

        },
        error: function (e) {
            console.log("ajax 요청 실패..");
            console.log(e)
        }
    });
});


// 마이페이지 프로필 수정
function modifyProfile() {
    $('#formFile').on('change',function(){
        let file = $(this).get(0).files[0];
        console.log($(this).get(0).files[0]);
        $('#profileImg').attr("src", URL.createObjectURL(file));
    })
}


//dialog
//#2. script에 아래 함수 복사해서 붙이기
/*
function jqAlert(outputMsg, titleMsg, onCloseCallback) {
    if (!titleMsg)
        titleMsg = 'Alert';

    if (!outputMsg)
        outputMsg = 'No Message to Display.';

    $("<div></div>").html(outputMsg).dialog({
        title: titleMsg,
        resizable: false,
        modal: true,
        buttons: {
            "OK": function () {
                $(this).dialog("close");
            }
        },
        close: function() { onCloseCallback();
            // Cleanup node(s) from DOM
            $(this).dialog('destroy').remove();
        }
    });
}

//#3. 원하는 위치에서아래 형식 참고해서 jqAlert 호출

jqAlert("로그인 성공!!!", "로그인 확인 창", function(){ window.location.href = "/" });

*/

//마이페이지 수정시 validate
// $.ajax({
//     url: "/member/mypage/modifyform",
//     type: "post",
//     data: "email",
//     dataType: "json",
//     contentType: "application/json; charset = utf - 8",
//
//
// });