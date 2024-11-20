$(document).ready(function () {
    const _tag =
        {
            '#loginEvent': function () {
                $('#loginModal').modal('show');
            },
            '#loginBtn': function (){
                var id = $("#userID").val();
                var pw = $("#password").val();
                console.log('아이디:', id);
                console.log('비밀번호:', pw);

                $.ajax({
                    url: '/login',
                    type: 'POST',
                    data: {
                        'username': id,
                        'password': pw
                    },
                    success: function (response) {
                        if(response.success) {
                            $('#loginModal').hide();

                            $('#logoutEvent').show();

                        }
                    }
                });

            }
            , '#joinEvent': function () {
                $('#joinModal').modal('show');
            }
            , '#joinBtn': function () {
                $('#joinModal').modal('show');
            }
            , '#leaveEvent': function () {
                $('#leaveModal').modal('show');
            }
            , '#logoutEvent': function () {
                console.log('logoutEvent');
                $.ajax({
                    url: '/logout',
                    type: 'GET',
                    success:function (){
                        $('#loginEvent').show();
                        $('#logoutEvent').hide();
                    }
                })
            }
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
