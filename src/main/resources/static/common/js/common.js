$(document).ready(function () {
    const _tag =
        {
            '#loginEvent': function () {
                $('#loginModal').modal('show');
            }
            , '#settingEvent': function () {
                console.log('settingEvent');
            }
            , '#profileEvent': function () {
                console.log('profileEvent');
            }
            , '#logoutEvent': function () {
                console.log('logoutEvent');
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
