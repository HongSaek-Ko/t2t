$(document).ready(function () {
    // bootstrap modal에는 일반적인 방법으로는 autofocus가 되지 않는다
    // 이때 첫번째 input tag에 cursor를 위치하게 하는 jQuery by 쌤
    $('.modal').on('shown.bs.modal', function () {
        $(this).find('input:first').focus();
    });

    const _tag =
        {
            '#loginEvent': function () {
                // $('#floatingInput').focus();
                $('#loginModal').modal('show');
            },

            '#joinEvent': function () {
                $('#joinModal').modal('show');
            },

            '#joinBtn': function () {
                $('#joinModal').modal('show');
            },

            '#leaveEvent': function () {
                $('#leaveModal').modal('show');
            },

            // 아이디 찾기 모달 보여주기
            '#findIdEvent': function () {
                $('#loginModal').modal('hide');
                $('#findIdModal').modal('show');
            },
            // 찾은 아이디 결과를 모달을 통해 보여주기

            // 비밀번호 찾기 모달 보여주기
            '#findPwdEvent': function () {
                $('#loginModal').modal('hide');
                $('#findPwdModal').modal('show');
            },
            // 비밀번호 찾기를 통한 임시 비밀번호 보여주기
            '#findPwdSubmit': function () {
                // ajax로 findPwdResult 호출!
                // url, type, data(name, email), f:success, f:error
                // 요청할때 보내줄 데이터 가져오기
                let _usrId = $('#findPwdUsrId').val();
                console.log(_usrId)
                let _name = $('#findPwdNm').val();
                let _email = $('#findPwdEmail').val();
                // 보내줄 데이터 MemberDTO 형태에 맞는 JS 객체로 만들기 -> JSON으로 변환해서 데이터 전송
                let _data = {
                    usrId: _usrId,
                    nm: _name,
                    email: _email
                };
                console.log(_data)
                $.ajax({
                    url: '/findPwdResult',
                    type: 'POST',
                    data: JSON.stringify(_data),
                    contentType: 'application/json;charset=utf-8',
                    success: function (pwdResult) {
                        console.log(pwdResult); // 컨트롤러에서 돌려주는 데이터 받았는지 확인
                        // 돌려받은 데이터 꺼내서 모달 화면에 뿌리고
                        $('#findPwdWrite').text(pwdResult);
                        // 모달 보여주기
                        $('#findPwdModal').modal('hide');
                        $('#findPwdResultModal').modal('show');
                    },
                    error: function (e) {
                        console.log("findPwdResult post 요청 실패....");
                    }
                });
            },
            // 여러 모달 페이지에서 로그인 또는 비밀번호 찾기 모달 열기 시작 부분 by Moon
            '#loginEvent1': function () {
                $('#findIdModal').modal('hide');
                $('#loginModal').modal('show');
            },

            '#findPwdEvent1': function () {
                $('#findIdModal').modal('hide');
                $('#findPwdModal').modal('show');
            },

            '#loginEvent2': function () {
                $('#findIdResultModal').modal('hide');
                $('#loginModal').modal('show');
            },

            '#findPwdEvent2': function () {
                $('#findIdResultModal').modal('hide');
                $('#findPwdModal').modal('show');
            },

            '#loginEvent3': function () {
                $('#findPwdModal').modal('hide');
                $('#loginModal').modal('show');
            },

            '#loginEvent4': function () {
                $('#findPwdResultModal').modal('hide');
                $('#loginModal').modal('show');
            },

            '#findPwdEvent3': function () {
                $('#findPwdResultModal').modal('hide');
                $('#findPwdModal').modal('show');
            },
            // 여러 모달 페이지에서 로그인 또는 비밀번호 찾기 모달 열기 끝 부분 by Moon

        };
    console.log(_tag);
    register_event(_tag);

    $('#loginSubmit').click(function () {
        loginSubmit();
    });

    $('#floatingPassword').keyup(function (e) {
        if (e.keyCode == 13) {
            loginSubmit();
        }
    });

    $("#findIdSubmit").click(function () {
        //     ajax로 findIdCheck 호출!
        //     url, type, data(name, email), f:success, f:error
        //     요청할때 보내줄 데이터 가져오기
        let _name = $('#findIdNm').val();
        console.log(_name);
        let _email = $('#findIdEmail').val();
        // 보내줄 데이터 MemberDTO 형태에 맞는 JS 객체로 만들기 -> JSON으로 변환해서 데이터 전송
        let _data = {
            nm: _name,
            email: _email
        };
        console.log(_data);
        $.ajax({
            url: '/findIdCheck',
            type: 'POST',
            data: JSON.stringify(_data),
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                console.log(result); // 컨트롤러에서 돌려주는 데이터 받았는지 확인
                // 돌려받은 데이터 꺼내서 모달 화면에 뿌리고
                $('#findIdWrite').text(result);
                // 모달 보여주기
                $('#findIdModal').modal('hide');
                $('#findIdResultModal').modal('show');
            },
            error: function (e) {
                console.log("findIdCheck post 요청 실패....");
            }
        });
    });

    function register_event(_targets) {
        let _keys = Object.keys(_targets);
        // $(_keys[0]).on('click', function() {
        //     _targets[_keys[0]]();
        // });
        $.each(_keys, function (_index, _el) {
            $(_el).on('click', function () {
                _targets[_el]();
                console.log(_el);
            });
        });

    }
    joinEvent();
    duplicateId();
    logoutEvent();
    leaveEvent();
});

// 기존 default 프로필 사진을 선택한 사진으로 변경하는 함수 by Moon
function loadFile(target, input) {
    console.log(input.files[0]);
    let file = input.files[0];
    $(target).attr("src", URL.createObjectURL(file));
}

// 유효성 검사(Not null 속성값이 다 입력 되었는지 확인) 함수 by Moon
// function fieldCheck() {
//     let form = document.getElementById("joinForm")
//     console.log(form);
//     if(!form.usrId.value) {
//         alert("아이디를 입력하세요~~");
//         return false;
//     }
//     if(!form.passwd.value) {
//         alert("비밀번호를 입력하세요~~");
//         return false;
//     }
//     if(!form.pwch.value) {
//         alert("비밀번호 확인란를 입력하세요~~");
//         return false;
//     }
//     if(!form.nm.value) {
//         alert("이름을 입력하세요~~");
//         return false;
//     }
//     if(!form.bankNm.value) {
//         alert("은행이름을 입력하세요~~");
//         return false;
//     }
//     if(!form.bankAcnt.value) {
//         alert("은행 계좌번호를 입력하세요~~");
//         return false;
//     }
//     if(!form.bankAcntOwr.value) {
//         alert("예금주를 입력하세요~~");
//         return false;
//     }
//
//     if(form.passwd.value != form.pwch.value) {
//         alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
//         return false;
//     }
//
//     if($("#idCheck").data("success") == false) {
//         alert("존재하는 아이디 입니다.")
//         return false;
//     }
// }

// // Ajax를 이용한 회원탈퇴
// function withdrawEvent () {
//     $("leaveCompleteBtn").click(function() {
//         let passwd = $('input[name=LeavePassword]').val()
//         $.ajax({
//             url:  "member/idPwAvailAjax",
//             type: "post",
//             data: {passwd : passwd},
//             success: function (result) {
//                 console.log(result);
//                 if(result === false) {
//                     alert("비밀번호가 틀렸습니다!");
//                 }
//             },
//             error: function (e) {
//                 console.log("ajax 요청 실패!!");
//                 console.log(e);
//             }
//
//         });
//     });
// }
function joinEvent() {
    function joinSubmit() {
        $("#joinForm").validate({
            rules: {
                usrId: {required: true},
                passwd: {required: true},
                pwch: {
                    required: true
                },
                nm: {required: true},
                email: {required: true},
                bankNm: {required: true},
                bankAcnt: {required: true},
                bankAcntOwr: {required: true}
            },
            messages: {
                usrId: "ID를 입력해 주세요.",
                passwd: "비밀번호를 입력해 주세요.",
                pwch: "비밀번호를 한번 더 입력해 주세요.",
                nm: "이름을 입력해 주세요.",
                email: "이메일을 입력해 주세요.",
                bankNm: "은행명을 입력해 주세요.",
                bankAcnt: "계좌번호를 입력해 주세요.",
                bankAcntOwr: "예금주를 입력해 주세요"
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
        $('#joinForm').submit();

    }

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
            close: function () {
                onCloseCallback();
                /* Cleanup node(s) from DOM */
                $(this).dialog('destroy').remove();
            }
        });
    }

    $('#joinCompleteBtn').click(function () {
        joinSubmit();
    });

}

function leaveEvent() {
    function leaveSubmit() {
        $("#leaveForm").validate({
            rules: {
                passwd: {
                    required: true
                }
            },
            messages: {
                passwd: "비밀번호를 입력해 주세요."
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
        $('#leaveForm').submit();
    }

    $('#leaveCompleteBtn').click(function () {
        leaveSubmit();
    });

    $('#LeavePassword').keyup(function (e) {
        if (e.keyCode == 13) {
            leaveSubmit();
        }
    });
}

function logoutEvent() {
    $("#logoutEvent").click(function () {
        window.location.href = "/logout";
    });
}

function duplicateId() {
    // id값이 "floatingJoinInput"인 input 태그에 변경사항이 있으면 이벤트 발생
    $("#floatingJoinInput").change(function () {
        let usrId = $("#floatingJoinInput").val();
        $.ajax({
            url: "/idAvailAjax",
            type: "post",
            data: {usrId: usrId},
            success: function (result) {
                console.log(result);
                $("#idCheck").val(result.result);
                if (result.success == "false") {
                    $("#idCheck").css("color", "red");
                    $("#idCheck").data("success", false);
                } else {
                    $("#idCheck").css("color", "blue");
                    $("#idCheck").data("success", true);
                }
            },
            error: function (e) {
                console.log("ajax 요청 실패!!");
                console.log(e);
            }
        });
    });
}


function loginSubmit() {
    if (!$('#flexCheckDefault').is(':checked')) {
        $('#flexCheckDefault').prop("checked", true);
        $('#flexCheckDefault').val(false);
    } else {
        $('#flexCheckDefault').val(true);
    }
    $("#loginForm").validate({
        rules: {
            usrId: {
                required: true
            },
            passwd: {
                required: true
            }
        },
        messages: {
            usrId: "ID를 입력해 주세요.",
            passwd: "비밀번호를 입력해 주세요."
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
    $('#loginForm').submit();
}




