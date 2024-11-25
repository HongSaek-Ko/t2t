$(document).ready(function () {
    const _tag =
        {
            '#loginEvent': function () {
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
            '#findIdSubmit': function () {
                // ajax로 findIdCheck 호출!
                // url, type, data(name, email), f:success, f:error
                // 요청할때 보내줄 데이터 가져오기
                let _name = $('#findIdNm').val();
                let _email = $('#findIdEmail').val();
                // 보내줄 데이터 MemberDTO 형태에 맞는 JS 객체로 만들기 -> JSON으로 변환해서 데이터 전송
                let _data = {
                    nm: _name,
                    email: _email
                };

                $.ajax({
                    url: '/findIdCheck',
                    type: 'POST',
                    data: JSON.stringify(_data),
                    contentType: 'application/json;charset=utf-8',
                    success: function(result){
                        console.log(result); // 컨트롤러에서 돌려주는 데이터 받았는지 확인
                        // 돌려받은 데이터 꺼내서 모달 화면에 뿌리고
                        $('#findIdWrite').text(result);
                        // 모달 보여주기
                        $('#findIdModal').modal('hide');
                        $('#findIdResultModal').modal('show');
                    },
                    error: function (e){
                        console.log("findIdCheck post 요청 실패....");
                    }
                });
            },
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
                    success: function(pwdResult){
                        console.log(pwdResult); // 컨트롤러에서 돌려주는 데이터 받았는지 확인
                        // 돌려받은 데이터 꺼내서 모달 화면에 뿌리고
                        $('#findPwdWrite').text(pwdResult);
                        // 모달 보여주기
                        $('#findPwdModal').modal('hide');
                        $('#findPwdResultModal').modal('show');
                    },
                    error: function (e){
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
