$(document).ready(function () {
    registerEvent(['#rechargeEvent', '#exchageEvent']);
});

function registerModalEvent() {
    $('.modal.fade #addMileBtn').on('click', function () {
        $.ajax(
    {
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